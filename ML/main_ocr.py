import cv2
import numpy as np
import pytesseract

#Parameter
per = 25
pixelThershold = 500
roi = [[(160, 860), (664, 928), 'text', 'med1'], 
       [(162, 942), (664, 1000), 'text', 'med2'], 
       [(164, 1008), (660, 1066), 'text', 'med3'], 
       [(158, 1072), (662, 1130), 'text', 'med4'], 
       [(164, 1304), (662, 1362), 'text', 'med5'], 
       [(162, 1374), (666, 1440), 'text', 'med6'], 
       [(160, 1450), (660, 1512), 'text', 'med7'], 
       [(154, 1520), (660, 1580), 'text', 'med8']]

# Open the image files.
img1_color = cv2.imread('custom_receipt.png')  # Image to be aligned.
img2_color = cv2.imread('custom_receipt.png')    # Reference image.
  
# Convert to grayscale.
img1 = cv2.cvtColor(img1_color, cv2.COLOR_BGR2GRAY)
img2 = cv2.cvtColor(img2_color, cv2.COLOR_BGR2GRAY)
height, width = img2.shape

# Create ORB detector with 5000 features.
orb_detector = cv2.ORB_create(5000)
  
# Find keypoints and descriptors.
# The first arg is the image, second arg is the mask
#  (which is not reqiured in this case).
kp1, d1 = orb_detector.detectAndCompute(img1, None)
kp2, d2 = orb_detector.detectAndCompute(img2, None)

# Match features between the two images.
# We create a Brute Force matcher with 
# Hamming distance as measurement mode.
matcher = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck = True)

# Match the two sets of descriptors.
matches = matcher.match(d1, d2)
  
# Sort matches on the basis of their Hamming distance.
matches.sort(key = lambda x: x.distance)
  
# Take the top 90 % matches forward.
matches = matches[:int(len(matches)*90)]
no_of_matches = len(matches)

# Define empty matrices of shape no_of_matches * 2.
p1 = np.zeros((no_of_matches, 2))
p2 = np.zeros((no_of_matches, 2))
  
for i in range(len(matches)):
  p1[i, :] = kp1[matches[i].queryIdx].pt
  p2[i, :] = kp2[matches[i].trainIdx].pt
  
# Find the homography matrix.
homography, mask = cv2.findHomography(p1, p2, cv2.RANSAC)

transformed_img = cv2.warpPerspective(img1_color,
                    homography, (width, height))
#cv2_imshow(transformed_img)

imgShow = transformed_img.copy()
imgMask = np.zeros_like(imgShow)

myData = []

#print(f'====================== Extracting Data From Receipt ===================================')

for x, r in enumerate(roi):

    cv2.rectangle(imgMask, ((r[0][0]), r[0][1]), ((r[1][0]), r[1][1]), (0, 255, 0), cv2.FILLED)
    imgShow = cv2.addWeighted(imgShow, 0.99, imgMask, 0.1, 0)

    imgCrop = transformed_img[r[0][1]:r[1][1], r[0][0]:r[1][0]]
    #cv2_imshow(imgCrop)
    imgGray = cv2.cvtColor(imgCrop, cv2.COLOR_BGR2GRAY)
    imgThresh = cv2.threshold(imgGray, 170, 255, cv2.THRESH_BINARY_INV)[1]
    totalPixels = cv2.countNonZero(imgThresh)

    if r[2] == 'text':
        if totalPixels > pixelThershold: 
            print(f'{r[3]} : {pytesseract.image_to_string(imgCrop)}')
            myData.append(pytesseract.image_to_string(imgCrop)) #bisa diganti sama model nn yang lain
import cv2
import numpy as np
import pytesseract

pytesseract.pytesseract.tesseract_cmd = r"C:\Program Files\Tesseract-OCR\tesseract.exe"

#Parameter
per = 25
pixelThershold = 500
roi = [[(100, 488), (532, 556), 'text', 'obat1'], 
    [(92, 558), (534, 604), 'text', 'obat2'], 
    [(94, 604), (528, 662), 'text', 'obat3'], 
    [(100, 668), (534, 722), 'text', 'obat4'], 
    [(96, 736), (546, 776), 'text', 'obat5']]

# Open the image files.
img1_color = cv2.imread('resep_coba2.png')  # Image to be aligned.
img2_color = cv2.imread('resep.png')    # Reference image.
  
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

#pytesseract.tesseract_cmd = path_to_tesseract

for x, r in enumerate(roi):

    cv2.rectangle(imgMask, ((r[0][0]), r[0][1]), ((r[1][0]), r[1][1]), (0, 255, 0), cv2.FILLED)
    imgShow = cv2.addWeighted(imgShow, 0.99, imgMask, 0.1, 0)

    imgCrop = transformed_img[r[0][1]:r[1][1], r[0][0]:r[1][0]]
    #cv2_imshow(imgCrop)

    if r[2] == 'text':
        print(f'{r[3]} : {pytesseract.image_to_string(imgCrop)}')
        myData.append(pytesseract.image_to_string(imgCrop)) #bisa diganti sama model nn yang lain
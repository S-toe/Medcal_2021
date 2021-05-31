import cv2
import numpy as np
import pytesseract
import os

pytesseract.pytesseract.tesseract_cmd='C:\\Program Files\\Tesseract-OCR\\tesseract.exe'

imgQ=cv2.imread('resep_2976x3664.png')
h,w,c=imgQ.shape
imgQ=cv2.resize(imgQ,(w//4,h//4))

orb=cv2.ORB_create(1000)
kp1,des1=orb.detectAndCompute(imgQ, None)
impKp1=cv2.drawKeypoints(imgQ, kp1,None)

cv2.imshow("KeyPointsQuery",impKp1)
cv2.imshow("output",imgQ)
cv2.waitKey(0)



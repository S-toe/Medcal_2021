from flask import Flask, request
import io
from PIL import Image
from pytesseract import pytesseract
app = Flask(__name__)

path_to_tesseract = r"C:\Program Files\Tesseract-OCR\tesseract.exe"

	
@app.route('/image', methods = ['GET', 'POST'])
def handle_request():
	if request.method == 'POST':
		img = request.files['image'].read()
		pytesseract.tesseract_cmd = path_to_tesseract
		text = pytesseract.image_to_string(Image.open(io.BytesIO(img)))
		return text
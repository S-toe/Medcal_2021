import numpy as np
import pandas as pd
import cv2
import keras
import random
import matplotlib.pyplot as plt
from keras import backend as K
from keras.layers.recurrent import LSTM,GRU
import tensorflow as tf

test_data=pd.read_csv('Sampled_data_100.csv')

test_data.drop(['Unnamed: 0'],axis=1,inplace=True)
#print(test_data.shape)
#test_data.head()

#Letters present in the Label Text
letters= '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ'

def words_from_labels(labels):

    txt=[]
    for ele in labels:
        if ele == len(letters): # CTC blank space
            txt.append("")
        else:
            #print(letters[ele])
            txt.append(letters[ele])
    return "".join(txt)

def decode_label(out):

    # out : (1, 48, 37)
    out_best = list(np.argmax(out[0,2:], axis=1))

    out_best = [k for k, g in itertools.groupby(out_best)]  # remove overlap value

    outstr=words_from_labels(out_best)
    return outstr

#image height
img_h=32
#image width
img_w=170
#image Channels
img_c=1
# classes for softmax with number of letters +1 for blank space in ctc
num_classes=len(letters)+1
batch_size=64
max_length=15 # considering max length of ground truths labels to be 15

def model_create(drop_out_rate=0.35):
    
    if K.image_data_format() == 'channels_first':
        input_shape = (1, img_w, img_h)
    else:
        input_shape = (img_w, img_h, 1)
       
    model_input=Input(shape=input_shape,name='img_input',dtype='float32')

    # Convolution layer
    model = tf.keras.Sequential([
        model_input,
        tf.keras.layers.Conv2D(64, (3, 3), padding='same', name='conv1', kernel_initializer='he_normal'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.activations.relu(),
        tf.keras.layers.MaxPooling2D(pool_size=(2, 2), name='max1'),
        tf.keras.layers.Conv2D(64, (3, 3), padding='same', name='conv2', kernel_initializer='he_normal'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.activations.relu(),
        tf.keras.layers.MaxPooling2D(pool_size=(2, 2), name='max2'),
        tf.keras.layers.Conv2D(64, (3, 3), padding='same', name='conv3', kernel_initializer='he_normal'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.activations.relu(),
        tf.keras.layers.Conv2D(64, (3, 3), padding='same', name='conv4', kernel_initializer='he_normal'),
        tf.keras.layers.Dropout(drop_out_rate),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.activations.relu(),
        tf.keras.layers.MaxPooling2D(pool_size=(2, 2), name='max3'),
        tf.keras.layers.Conv2D(64, (3, 3), padding='same', name='conv5', kernel_initializer='he_normal'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.activations.relu(),
        tf.keras.layers.Conv2D(64, (3, 3), padding='same', name='conv6'),
        tf.keras.layers.Dropout(drop_out_rate),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.activations.relu(),
        tf.keras.layers.MaxPooling2D(pool_size=(2, 2), name='max4'),
        tf.keras.layers.Conv2D(64, (3, 3), padding='same', name='conv7', kernel_initializer='he_normal'),
        tf.keras.layers.Dropout(0.25),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.activations.relu(),
        tf.keras.layers.Reshape(target_shape=((42, 1024)), name='reshape'),
        tf.keras.layers.Dense(64, activation='relu', kernel_initializer='he_normal', name='dense1'),
        tf.keras.layers.Bidirectional(LSTM(256, return_sequences=True, kernel_initializer='he_normal'), merge_mode='sum'),
        tf.keras.layers.Bidirectional(LSTM(256, return_sequences=True, kernel_initializer='he_normal'), merge_mode='concat'),
        tf.keras.layers.Dense(num_classes, kernel_initializer='he_normal',name='dense2')
        tf.keras.layers.activations('softmax', name='softmax')

    ])
    return Model(inputs=[model_input], outputs=y_pred)
    
def Image_Prediction(model,test_img_path):
    
    start=datetime.now()
    
    test_img=cv2.imread(test_img_path)
    test_img_resized=cv2.resize(test_img,(170,32))
    test_image=test_img_resized[:,:,1]
    test_image=test_image.T 
    test_image=np.expand_dims(test_image,axis=-1)
    test_image=np.expand_dims(test_image, axis=0)
    test_image=test_image/255
    model_output=model.predict(test_image)
    predicted_output=decode_label(model_output)
    print("Predicted Text in the Image: ", predicted_output)
    #print("Time Taken for Processing: ",datetime.now()-start)

model=model_create()
#model.summary()

test_img = 'custom_receipt.png'
image_Prediction(model,test_img)


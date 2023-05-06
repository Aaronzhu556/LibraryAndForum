import pandas as pd
from keras.models import load_model
import numpy as np
from tensorflow import keras # tensorflow == 2.X
from keras import Model
import keras.backend as K
from tqdm import tqdm
from keras.layers import Embedding,Reshape,Input,Dot
K.clear_session()
def recommend(user_id,user_embedding_learnt,book_embedding_learnt,N): #这是给用户做推荐
    books = user_embedding_learnt[user_id-1] @ book_embedding_learnt.T
    #print(books)
    return np.argpartition(books, -N)[-N:]

if __name__=="__main__":
    model = load_model('D:/PycharmFile/speak/recommend_book.h5')
    # 获得用户和项目的嵌入矩阵
    user_embedding_learnt = model.get_layer(name='User-Embedding').get_weights()[0]
    book_embedding_learnt = model.get_layer(name='Book-Embedding').get_weights()[0]
    # model.predict([[1],[2]])

    topN = recommend(1, user_embedding_learnt, book_embedding_learnt, 5)

    print(topN.tolist())
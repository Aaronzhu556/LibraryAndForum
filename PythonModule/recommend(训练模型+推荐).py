
#1 卷积神经网络层数
#2
#这是图书推荐算法  我是基于TensorFlow的keras框架构建的一个卷积神经网络，然后利用比较经典的推荐算法：MF（矩阵分解）算法对数据集进行训练，得到两个
#隐藏的向量，用户向量和图书向量，然后通过这个两个向量的乘积做推荐，乘出来数据越大的就排在前面


import pandas as pd
import numpy as np
from tensorflow import keras # tensorflow == 2.X
from keras import Model
import keras.backend as K
from tqdm import tqdm
from keras.layers import Embedding,Reshape,Input,Dot
K.clear_session()
rating = pd.read_csv("./ratings.dat",sep="::",names=["user_id", "item_id", "rating", "timestamp"])
num_user = np.max(rating["user_id"])
num_book = np.max(rating["item_id"])
print(num_user,num_book,len(rating))

def recommend_model(num_user,num_book,n_latent_factors):
    '''
    input_uer = Input(shape=[1], dtype="int32")
    model_uer = Embedding(num_user + 1, n_latent_factors, input_length=1)(input_uer)
    uer_vec = Reshape((n_latent_factors,))(model_uer)

    input_book = Input(shape=[1], dtype="int32")
    model_book = Embedding(num_book + 1, n_latent_factors, input_length=1)(input_book)
    book_vec = Reshape((n_latent_factors,))(model_book)

    out = Dot(1)([uer_vec, book_vec])
    model = Model(inputs=[input_uer, input_book], outputs=out)
    model.compile(loss='mse', optimizer='Adam')
    model.summary()
    return model
    '''
    #这里再构建卷积神经网络
#刚刚那个是训练数
    book_input = keras.layers.Input(shape=[1], name='Item')
    book_embedding = keras.layers.Embedding(num_book + 1, n_latent_factors, name='Book-Embedding')(book_input)
    book_vec = keras.layers.Flatten(name='Flattenbooks')(book_embedding)
    # 设置用户参数
    user_input = keras.layers.Input(shape=[1], name='User')
    user_embedding = keras.layers.Embedding(num_user + 1, n_latent_factors, name='User-Embedding')(user_input)
    user_vec = keras.layers.Flatten(name='FlattenUsers')(user_embedding)
    # 计算项目向量与用户张量的点乘
    prod = keras.layers.dot([book_vec, user_vec], axes=1, name='DotProduct')
    # 创建用户-项目模型
    model = keras.Model([user_input, book_input], prod)
    # 设置模型优化器、损失函数、测量指标
    model.compile(optimizer='adam', loss='mean_squared_error', metrics=['mae', 'mse'])
    return model

def recommend(user_id,user_embedding_learnt,book_embedding_learnt,N): #这是给用户做推荐
    books = user_embedding_learnt[user_id-1] @ book_embedding_learnt.T
    #print(books)
    return np.argpartition(books, -N)[-N:]



if __name__=="__main__":
    model = recommend_model(num_user,num_book,n_latent_factors=100)
    model.summary()
    train_user = rating["user_id"].values
    train_book = rating["item_id"].values
    train_x = [train_user, train_book]
    train_y = rating["rating"].values
    model.fit(train_x,train_y,batch_size=100,epochs=1)
    model.save("recommend_book.h5")
    # 获得用户和项目的嵌入矩阵
    user_embedding_learnt = model.get_layer(name='User-Embedding').get_weights()[0]
    book_embedding_learnt = model.get_layer(name='Book-Embedding').get_weights()[0]
    #model.predict([[1],[2]])


    topN = recommend(1,user_embedding_learnt,book_embedding_learnt,5)
    print(topN.tolist())
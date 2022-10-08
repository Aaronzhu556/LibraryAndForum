import codecs
import os
import jieba
import numpy
import pandas
import sys
import re
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics import pairwise_distances

# 导入停顿词
pause = pandas.read_csv("D:\\PycharmFile\\speak\\stopwords", encoding='utf-8', quoting=3, names=['stopword'], index_col=False)
# 中文正则
zh_pattern = re.compile(u'[\u4e00-\u9fa5]')

file_paths = []
file_contents = []

# 递归获取所有文章并分词

if __name__ == "__main__":
    article_all = int (sys.argv[1])
    for root, dirs, files in os.walk("D:\\graduateproject2file\\"):
        for file in files:
            # 文件路径
            file_path = os.path.join(root, file)
            # 文件流句柄
            f_handle = codecs.open(file_path, "r", "utf-8")
            # 文件内容
            file_content = f_handle.read()
            # 关闭文件
            f_handle.close()
            content = ''
            # 分词
            for segment in jieba.cut(file_content):
                if zh_pattern.search(segment) and segment not in pause.stopword.values and len(
                        segment.strip().strip(b'\x00'.decode())) > 0:
                    content = content + " " + segment
            file_paths.append(file_path)
            file_contents.append(content)

    # 文章路径和文章内容分词拼接后的数据矿
    file_data_frame = pandas.DataFrame({
        'file_path': file_paths,
        'file_content': file_contents
    })

    # TF-IDF矩阵
    tfidf = CountVectorizer().fit_transform(file_data_frame['file_content'])

    # 相似度
    distance_matrix = pairwise_distances(
        tfidf,
        metric="cosine"
    )

    # 相似度数据矿，为了方便计算，相似度取反
    distance_matrix_data_frame = 1 - pandas.DataFrame(distance_matrix)
    distance_matrix_data_frame.columns = file_paths
    distance_matrix_data_frame.index = file_paths

    # 对相似度数据矿进行倒序，然后取前五列
    TFIDFSorted = numpy.argsort(distance_matrix, axis=1)[:, 0:6]

    # 取出每个相似度对应的文章路径
    similarity5 = pandas.Index(file_paths)[TFIDFSorted]
    # 组成文章与其最为相似的五个文章路径的数据矿

    similarityDF = pandas.DataFrame({
        'file_paths': file_paths,
        's1': similarity5[:, 0],
        's2': similarity5[:, 1],
        's3': similarity5[:, 2],
        's4': similarity5[:, 3],
        's5': similarity5[:, 4],
        's6': similarity5[:, 5]
    })

    order1 = numpy.array(similarityDF.iloc[article_all, :])
    print(order1[2:].tolist())
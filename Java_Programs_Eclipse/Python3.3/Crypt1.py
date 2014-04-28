import sys
import random



def strxor(a, b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])
    
a = [1,2,3,4,5]
b = [1,2,3,4,5]


sr= "Bhavik"
key = "abcff73acb"

xorstr = strxor(sr, key)
print (xorstr.encode(encoding='hex', errors='strict'))


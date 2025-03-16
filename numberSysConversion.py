# decimal to binary conversion
def decBin(num):
    org = num
    res = ''
    if num != 0:
        while num > 1:
            x =  num % 2
            res += str(x)
            num = num // 2
        res += '1'
        return int(res[::-1])
    else:
        return 0

# binary to decimal conversion
def binDec(num):
    num = str(num)
    res = 0
    length = len(num)
    for dig in num:
        res += int(dig) * (2 ** (length-1))
        length -= 1
    return res
       
# adding binary numbers   
def binAdd(x, y):
    res = ''
    carry = 0
    
    if len(str(x)) > len(str(y)):
        bin1rev = str(x)[::-1]
        bin2rev = str(y)[::-1]
    else:
        bin2rev = str(x)[::-1]
        bin1rev = str(y)[::-1]
        
    for digitpos in range(len(bin1rev)):
        if digitpos < len(bin2rev):
            temp = int(bin1rev[digitpos]) + int(bin2rev[digitpos]) + carry
        else:
                temp = int(bin1rev[digitpos]) + carry

        if temp == 0:
            res += '0'
            carry = 0
        elif temp == 1:
            res += '1'
            carry = 0
        elif temp == 2:
            res+= '0'
            carry = 1
        elif temp == 3:
            res += '1'
            carry = 1
     
    if carry == 1:
        res += '1'
        
    # print(x, ' + ', y, ' = ', res[::-1])
    return res[::-1]
    


def binMult(x, y):
    if len(str(x)) > len(str(y)):
        bin1rev = str(x)[::-1]
        bin2rev = str(y)[::-1]
    else:
        bin2rev = str(x)[::-1]
        bin1rev = str(y)[::-1]
    
    results = []
    
    for position in range(len(bin2rev)):
        carry = 0
        temp = ''
        for n in bin1rev:
            temp += str(int(bin2rev[position]) * int(n))
            # print(temp)
        
        results.append(temp[::-1] + (position * '0'))
    
    result = results[0]
    
    # print(x, ' x ', y, ' =')
    for i in range(len(results)-1):
        result = binAdd(result, results[i+1])
    return result



# binary to octal
def binOct(num):
    num = str(num)[::-1]
    res = ''
    for i in range(0, len(num), 3):
        res += str(binDec(num[i:i+3]))
        # print(num[i:i+3])
        # print(i)
    return res[::-1]



# octal to binary
def octBin(num):
    num = str(num)
    res = ''
    for i in num:
        temp = str(decBin(int(i)))
        while len(temp) < 3:
            temp = '0' + temp
        res += temp
    while res[0] == '0':
        res = res[1:]
    return res



# Exercise 2.1

bin2dec = [7,12,16,33,63]
dec2bin = [101,1011,11111,100000,100001]

print('Exercise 2.1')
for i in bin2dec:
    print(i, 'in decimal is', decBin(i), 'in binary')
    
for i in dec2bin:
    print(i, 'in binary is', binDec(i), 'in decimal')

print('--------------------------------------')

# Exercise 3.1

addition = [(10,1),(110,10),(101,11),(1001,1),(1000,111)]
print('Exercise 2.1')
for i in range(len(addition)):
    print(addition[i][0], '+', addition[i][1], '=', binAdd(addition[i][0],addition[i][1]))

print('--------------------------------------')

print('Exercise 4.1')
mult = [(101,10),(1101,101),(101,10),(1011,100000000),(111,111)]

for i in range(len(mult)):
    print(mult[i][0], '*', mult[i][1], '=', binMult(mult[i][0],mult[i][1]))
    
print('--------------------------------------')

print('Exercise 5.1')
bin2oct = [100110011, 1001, 1111000101010001010010]
oct2bin = [246, 2003, 3657234]

for i in bin2oct:
    print(i, 'in binary is', binOct(i), 'in octal  --!!-- not working properly')
    
for i in oct2bin:
    print(i, 'in octal is', octBin(i), 'in binary')

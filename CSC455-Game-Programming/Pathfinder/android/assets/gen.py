from random import randint

file = open('bigmap.txt', 'w+')
up = 25
for i in range(0,10):
    for j in range(0, up):
        decide = randint(0,100)
        if decide < 10:
            file.write("F")
            if j < (up - 1):
                file.write(" ")
        else:
            #weight = randint(1, 9)
            #file.write('%d' % weight)
            smallDecide = randint(0, 100)
            weight = 0
            if(smallDecide < 70):
                weight = randint(1, 4)
            else:
                weight = randint(3, 9)
            file.write('%d' % weight)
            if j < (up - 1):
                file.write(" ")
    file.write("\n")


from random import randint
from numbers import Number

width = int(input("Enter the width: "))
height = int(input("Enter the height: "))
telecount = int(input("Enter the telecount: "))
filename = str(input("Enter File name: "))

def makeMatrix():
    grid = []
    for i in range(0, height):
        line = []
        for j in range(0, width):
            line.append(0)
        grid.append(line)
    return grid

def makeGrid():
    grid = makeMatrix()
    for i in range(0, height):
        for j in range(0, width):
            decide = randint(0,100)
            if decide < 10:
                grid[i][j] = str("F")
            else:
                smallDecide = randint(0, 100)
                weight = 0
                if(smallDecide < 70):
                     weight = randint(1, 4)
                else:
                     weight = randint(3, 9)
                grid[i][j] = str(weight)
    return grid      

def addTeleports(grid):
    count = 0
    while count < telecount:
        added = False
        y = randint(0, height - 1)
        x = randint(0, width - 1)
        y2 = y
        x2 = x
        
        while y is y2 and x is x2:
            y2 = randint(0, height - 1)
            x2 = randint(0, width - 1)
            
        ox = grid[y][x]
        oy = grid[y2][x2]
        if isinstance(ox, Number) or ox is "F":
            if isinstance(oy, Number) or oy is "F":
                  grid[y][x] = "T" + str(count)
                  grid[y2][x2] = "T" + str(count)
                  added = True
        if added is True:
            count += 1
    return grid
            
def writeFile(grid):
    file = open(filename, 'w+')
    for i in range(0, height):
        for j in range(0, width):
            file.write(str(grid[i][j]))
            if j < (width - 1):
                file.write(" ")
        file.write("\n")

writeFile(addTeleports(makeGrid()))
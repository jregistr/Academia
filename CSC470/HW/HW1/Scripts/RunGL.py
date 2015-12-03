# File name: RunGL.py
# Author: Jeff Registre
# Date created: 9/03/2015

# Begin code

#Imports
import webbrowser
import os

vertPath = "C:/Users/Jeff/Documents/School/CSC470/HW/HW1/shaders/vert.glsl"
fragPath = "C:/Users/Jeff/Documents/School/CSC470/HW/HW1/shaders/frag.glsl"
htmlFile = "C:/Users/Jeff/Documents/School/CSC470/HW/HW1/html/SceneGraph.html"

firstStop = "id=\"shader-vs\""
secondStop = "id=\"shader-fs\""

    
def main ():
    compiled = open("compiled.html", "w")
    with open(htmlFile) as f:
        for line in f:
            if line.find(firstStop) is not -1:
                appendVert(compiled, line)
            elif line.find(secondStop) is not -1:
                appendFrag(compiled, line)
            else:
                compiled.write(line)
    compiled.close()  
    webbrowser.open_new_tab("compiled.html")
    com = raw_input("Enter \"Compile\" to save file or anything else to delete and quit: ")
    
    if com.find("compile") is -1:
        os.remove("compiled.html")
                            
def appendVert(f, at):
    f.write(at)
    with open(vertPath) as vertFile:
        for line in vertFile:
            if line.find("#") is -1:
                f.write(line)
    
def appendFrag(f, at):
    f.write(at)
    with open(fragPath) as fragFile:
        for line in fragFile:
            if line.find("#") is -1:
                f.write(line)
    
main()

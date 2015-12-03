__author__ = 'Jeff'
import re
import os
import zipfile
import smtplib
import getpass
from email import encoders
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart


userEmail = ''
toEmail = ''

while 1:
    userEmail = input("Please enter your email address: ")
    if not re.match("[^@]+@[^@]+\.[^@]+", userEmail):
        print("Invalid Email! try Again!")
    else:
        break

while 1:
    toEmail = input("Who are you sending this to?:")
    if not re.match("[^@]+@[^@]+\.[^@]+", toEmail):
        print("Invalid Email! try Again!")
    else:
        break

passw = getpass.getpass('Enter your password:')
subject = 'Jeff Registre - All assignments'
body = 'Hello!! This is python 3.4, on behalf of Jeff Registre, ' \
       'I am sending you this message.\n Attached to this email ' \
       'are my assignments for this class in a nicely generated zip file'


print("Processing.............")


def getallpaths():
    listdir = os.listdir("csc344")
    checked = []
    for curdir in listdir:
        if os.path.isdir("csc344/" + curdir):
            checked.append(curdir)
    return checked


def getallfilenames(subdir):
    filepath = "csc344/" + subdir
    filenames = os.listdir(filepath)
    return filenames


def getallfiles(subdir):
    filepath = "csc344/" + subdir
    filenames = os.listdir(filepath)
    files = []
    for name in filenames:
        files.append(name + "\n" + open(filepath + "/" + name).read())

    return files


def getregexed(regex, source):
    return re.findall(regex, source)


def postprocessfile(filepath, name):
    file = open(filepath, "r")
    lines = file.readlines()
    file.close()

    file = open(filepath, "w")
    added = []

    if name == "a1C":
        for line in lines:
            if line[7:14] != "include" and line not in added:
                file.write(line)
                added.append(line)
    elif name == "a5Python":
        for line in lines:
            if line[13:19] != "import" and line not in added:
                file.write(line)
                added.append(line)
    else:
        for line in lines:
            line = line.strip()
            if line not in added:
                added.append(line)
                file.write(line + "\n")
    file.close()


def file_len(fname):
    with open(fname) as f:
        return sum(1 for _ in f)


def generatehtml():
    filename = os.path.join("csc344/" + "a5/" + "index.html")
    html = open(filename, "w")
    html.write("<!DOCTYPE html>\n")
    html.write("<html>\n")
    html.write("<head lang=\"en\">\n")
    html.write("<meta charset=\"UTF-8\">\n")
    html.write("<title>Jeff R CSC344 Assignments</title>\n")
    html.write("<link rel=\"stylesheet\" href=\"bootstrap.min.css\">\n")
    html.write("</head>\n")
    html.write("<body class=\"container\" style=\"width:95%; min-height:100vh; border:2px solid black\">\n")

    alldirs = getallpaths()
    for currentdir in alldirs:
        allfilesindir = getallfilenames(currentdir)
        rowtitle = ''
        if currentdir == "a1":
            rowtitle = "A1-C Assignment"
        elif currentdir == "a2":
            rowtitle = "A2-Lisp Assignment"
        elif currentdir == "a3":
            rowtitle = "A3-Scala Assignment"
        elif currentdir == "a4":
            rowtitle = "A4-Prolog Assignment"
        elif currentdir == "a5":
            rowtitle = "A5-Python Assignment"

        html.write("<table class=\"table table-striped table-bordered table-hover \"> \n")

        html.write("<thead style=\"font-family:Algerian; font-weight:bold \"> \n")
        html.write("<tr>")
        html.write("<th class=\"col-xs-8\">\n")
        html.write(rowtitle + "\n")
        html.write("</th>\n")

        html.write("<th class= \"col-xs-4\"> \n")
        html.write("Line Count \n")
        html.write("</th> \n")

        html.write("</tr> \n")
        html.write("</thead> \n")

        html.write("<tbody style=\"font-family:Arial \"> \n")
        for filename in allfilesindir:
            html.write("<tr> \n")
            filepath = ""
            filelinecount = 0
            fullname = "csc344/" + currentdir + "/" + filename
            filelinecount = file_len(fullname)

            if currentdir == "a5":
                filepath = filename
            else:
                filepath = "../" + currentdir + "/" + filename

            if filename != "index.html":
                html.write("<td class=\"col-xs-8\">\n")
                html.write("<a " + "href=" + "\"" + filepath + "\"" + ">")
                html.write(filename)
                html.write("</a>\n")
                html.write("</td>")

                html.write("<td class=\"col-xs-4\">\n")
                html.write(str(filelinecount))
                html.write("</td>")

                html.write("</tr>\n")
        html.write("</tbody>\n")
        html.write("</table>\n")
    html.write("</body>\n")
    html.write("</html>")
    html.close()


def makezips():
    alldirs = getallpaths()
    archive = zipfile.ZipFile("csc344/archive.zip", "w", zipfile.ZIP_DEFLATED)
    for currentdir in alldirs:
        allfilesindir = getallfilenames(currentdir)
        for file in allfilesindir:
            archive.write("csc344/" + currentdir + "/" + file)
    archive.close()
    return "archive.zip"


def sendemail(zipname):
    messagehead = MIMEMultipart()
    messagehead['From'] = userEmail
    messagehead['to'] = toEmail
    messagehead['subject'] = subject

    messagebody = MIMEText(body)

    zipattachment = MIMEBase("Assignments", "zip")
    ziped = open("csc344/" + zipname, "rb")
    zipattachment.set_payload(ziped.read())
    encoders.encode_base64(zipattachment)
    zipattachment.add_header("Content-Disposition", "Attachment; filename= " + zipname)

    messagehead.attach(messagebody)
    messagehead.attach(zipattachment)

    mailserver = smtplib.SMTP("smtp.gmail.com:587")
    mailserver.starttls()
    mailserver.login(userEmail, passw)
    mailserver.sendmail(userEmail, toEmail, messagehead.as_string())
    mailserver.quit()


def processprojects():
    alldirs = getallpaths()
    allregex = "[_a-zA-Z][_a-zA-z0-9]*"
    lispregex = "[-*+/a-zA-Z_][-a-zA-Z0-9_]*"
    for currentdir in alldirs:
        allfilesindir = getallfiles(currentdir)
        for currentfile in allfilesindir:
            regexcurrent = getregexed(allregex, currentfile)
            first = regexcurrent.pop(0)
            second = regexcurrent.pop(0)
            fscon = first + "." + second
            if first == "a2Lisp":
                regexcurrent = getregexed(lispregex, currentfile)

            if second == "c" or second == "lsp" or second == "scala" or second == "pl" or second == "py":
                filename = os.path.join("csc344/" + currentdir, first + "-SymbolsFile.txt")
                file = open(filename, "w")
                file.write("Symbols file for " + fscon)
                for curgex in regexcurrent:
                    file.write("\n" + "[" + fscon + "," + curgex + "]")
                file.close()
                postprocessfile(filename, first)
    generatehtml()
    archivename = makezips()
    sendemail(archivename)
    print("done, check your email!")

processprojects()


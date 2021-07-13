from time import sleep

from pyfiglet import Figlet

if __name__ == '__main__':
    logo = Figlet(font='slant')
    print(logo.renderText("Hello Docker"))
    # print("Throwing exception in 10 seconds")
    # sleep(10)
    # raise ZeroDivisionError

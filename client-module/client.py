import requests
from datetime import date



response = requests.get("http://localhost:8000/api/v1/getCentros")

centros = []

for item in response.json():
    centros.append(item)

choice = 0
for centro in centros:
    print(f"{choice}-> {centro['nome']}")
    choice += 1

#params = {"nome": "Centro de Vacinação do João Pavia Pereira"}

#centro = requests.get("http://localhost:8000/api/v1/getCentro", params=params)

#print(centro.json())

#params = {"nome": "Miguel Agostinho","idade": 21, "data": datetime.now(),"email": "jperas243@gmail.com"}

#response = requests.get("http://localhost:8000/api/v1/autoAgendamento",params=params)

option = 0
while 1:

    print("--------Client---------")
    print("1) Centros de Vacinação")
    print("2) Escolher centro")
    print("3) Auto Agendamento")
    print("4) Confirmar Agendamento")
    print("5) Exit")

    option = input("Option: ")

    if option == "1" :
        print(option)

        response = requests.get("http://localhost:8000/api/v1/getCentros")

        centros = []

        for item in response.json():
            centros.append(item)

        choice = 0
        for centro in centros:
            print(f"{choice}-> {centro['nome']}")
            choice += 1
            
    elif option == "2" :

        centerName = input("Nome do Centro: ")
        params = {"nome": "Centro de Vacinação do João Pavia Pereira"}
        centro = requests.get("http://localhost:8000/api/v1/getCentro", params=params)
        print(centro)

    #elif option == 3:


    #elif option == 4:

    elif option == "5" :
        break

    else: 
        print("Opção Invalida")

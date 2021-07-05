import requests
from datetime import date

option = 0

print("--------Client-Module--------")

centros = []
response = requests.get("http://localhost:8000/api/v1/getCentros")

for item in response.json():
    centros.append(item)

choice = 1
for centro in centros:
    print(f"{choice}-> {centro['nome']}")
    choice += 1

choice = int(input("Escolha o centro que se deseja conectar: "))
print(centros[choice-1]['url'])

centerURL = centros[choice-1]['url']

while 1:

    print("-------------------------")
    print("1) Auto Agendamento")
    print("2) Confirmar Agendamento")
    print("3) Exit")

    option = input("Option: ")

    if option == "1" :
        
        params = {"nome": "Miguel Agostinho","idade": 21, "data": datetime.now(),"email": "jperas243@gmail.com"}            
        response = requests.get("http://localhost:8000/api/v1/autoAgendamento",params=params)
        print(response.json())

    elif option == "2" :
        centerURL = centros[choice-1]['url']


    elif option == "3" :
        break

    else: 
        print("Opção Invalida")

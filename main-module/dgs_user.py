import requests
from datetime import date

def startUI():
    option = 0

    print("--------DGS-Module--------")

    centros = []
    response = requests.get("http://localhost:8000/api/v1/getCentros")

    choice = 1
    for item in response.json():
        centros.append(item)
        print(f"{choice}-> {item['nome']}")
        choice += 1

    

    choice = int(input("Escolha o centro que se deseja conectar: "))
    print(centros[choice-1]['url'])

    centerURL = centros[choice-1]['url']

    while 1:

        print("-------------------------")
        print("1) Fornecer Vacinas")
        print("2) Relatorio")
        print("3) Exit")

        option = input("Option: ")

        if option == "1" :
            
            nVacinas = int(input("Insira o numero de vacinas: "))
            data = input("Insira a data: ")
            #tipo de vacina

            params = {"data": data,"nVacinas": nVacinas }            
            response = requests.get("http://localhost:8000/api/v1/fornecerVacinas",params=params)
            print(response.json())

        elif option == "2" :
            
            data = int((input("Insira o dia: ")))
            requestUrl = "http://localhost:8000/api/v1/relatorio"
            params = {"data": data}            
            response = requests.get(requestUrl,params=params)
            
            print(response.text)


        elif option == "3" :
            break

        else: 
            print("Opção Invalida")


if __name__ == '__main__':
    startUI()
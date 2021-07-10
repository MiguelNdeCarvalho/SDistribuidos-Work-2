import requests
from datetime import date

def startUI():
    option = 0

    print("--------Center-Module--------")

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
        print("1) Lista de Agendamentos")
        print("2) Stock de vacinas")
        print("3) Vacinação")
        print("4) Exit")


        option = input("Option: ")

        if option == "1" :
                  
            response = requests.get(centerURL+"getAgendamentos",params=params)
            print(response.json())

        elif option == "2" :
            
            response = requests.get(centerURL+"getStocks",params=params)
            print(response.json())
            
            print(response.text)

        elif option == "3" :
            cc = int((input("Insira o numero do cartão de cidadão: ")))
            tipo = input("Insira o tipo da vacina: ")

            #tipo de vacina

            params = {"tipoVacina": tipo}            
            response = requests.post(f"{centerURL}setVacinado/{cc}",params=params)
            print(response.text)

        elif option == "4" :
            break

        else: 
            print("Opção Invalida")


if __name__ == '__main__':
    startUI()
import requests
from datetime import date

def startUI():
    option = 0

    print("--------Client-Module--------")

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
        print("1) Auto Agendamento")
        print("2) Informação do Agendamento")
        print("3) Exit")

        option = input("Option: ")

        if option == "1" :
            
            cc = int(input("Insira o numero do cartão de cidadão: "))
            nome = input("Insira o nome: ")
            idade = int(input("Insira a idade: "))
            email = input("Insira o email: ")

            params = {"nome": nome,"idade": idade, "data": datetime.now(),"email": email}            
            response = requests.get("http://localhost:8000/api/v1/autoAgendamento",params=params)
            print(response.json())

        elif option == "2" :
            
            cc = int((input("Insira o numero do cartão de cidadão: ")))
            requestUrl = centerURL + "getAgendamentoStatus"
            params = {"cc": cc}            
            response = requests.get(requestUrl,params=params)
            
            print(response.text)


        elif option == "3" :
            break

        else: 
            print("Opção Invalida")


if __name__ == '__main__':
    startUI()
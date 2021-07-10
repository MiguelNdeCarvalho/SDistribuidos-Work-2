import datetime
import requests

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

            request = {"cc": cc, "nome": nome, "idade": idade, "email": email, "data": f"{datetime.date.today()}"}            

            response = requests.post(f"{centerURL}autoAgendamento", json=request)

        elif option == "2" :
            
            cc = int((input("Insira o numero do cartão de cidadão: ")))
            requestUrl = centerURL + "getAgendamentoStatus"
            params = {"cc": cc}            
            response = requests.get(requestUrl,params=params)

            if response.text == "null":
                print("O seu agendamento ainda se encontra pendente!")
            elif response.text == "true":
                print("O seu agendamento encontra-se confirmado!")
            elif response.text == "false":
                print("É necessário proceder à remarcação do agendamento!")
            else:
                print("Resposta inválida")

        elif option == "3" :
            break

        else: 
            print("Opção Invalida")


if __name__ == '__main__':
    startUI()

import requests

def cliente():
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

    centerURL = centros[choice-1]['url']

    while 1:

        print("-------------------------")
        print("1) Auto Agendamento")
        print("2) Informação do Agendamento")
        print("3) Exit")

        option = input("Opção: ")

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

            if response.text == "Not Found":
                print("Não existe um agendamento para esse número do cartão de cidadão")
            elif response.text == "null":
                print("O seu agendamento ainda se encontra pendente!")
            elif response.text == "true":
                print("O seu agendamento encontra-se confirmado!")
            elif response.text == "false":
                print("É necessário proceder à remarcação do agendamento!")
                print("Deseja proceder à remarcação?")
                print("s) Proceder ao reagendamento\nn) Voltar ao menu anterior")
                option = input("Opção: ")

                if option == "s":
                    data = input("Insira a data desejada (dd-mm-yyyy): ")
                    date = timeparser.parse(data).date().isoformat()
                    requestUrl = centerURL + "updateAgendamento"
                    params = {"cc": cc, "data": date}
                    response = requests.put(requestUrl, params=params)
            else:
                print("Resposta inválida")

        elif option == "3" :
            exit(0)

        else: 
            print("Opção Invalida")

def centro():
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

def dgs():
    option = 0

    print("--------DGS-Module--------")
    while 1:

        print("1) Fornecer Vacinas")
        print("2) Relatorio")
        print("3) Exit")

        option = input("Option: ")

        if option == "1" :
            
            n_vacinas = int(input("Insira o numero de vacinas: "))
            data = input("Insira a data: ")
            tipo = input("Insira o tipo da vacina: ")

            #tipo de vacina

            params = {"data":data,"n_vacinas": n_vacinas,"tipo":tipo}            
            response = requests.get("http://localhost:8000/api/v1/fornecerVacinas",params=params)
            print(response.text)

        elif option == "2" :
            
            data = input("Insira o dia: ")
            requestUrl = "http://localhost:8000/api/v1/nTotalVacinas"
            params = {"data": data}            
            response = requests.get(requestUrl,params=params)
            
            print(response.json())


        elif option == "3" :
            break

        else: 
            print("Opção Invalida")

if __name__ == '__main__':
    cliente()
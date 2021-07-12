import argparse
import requests
import dateutil.parser as timeparser

DGSURL = "http://localhost:8000/api/v1/"


def cliente():
    option = 0

    print("--------Módulo-Cliente--------")

    centros = []
    response = requests.get(f"{DGSURL}getCentros")

    choice = 1
    for item in response.json():
        centros.append(item)
        print(f"{choice}-> {item['nome']}")
        choice += 1

    choice = int(input("Escolha o centro a que se deseja conectar: "))

    centerURL = centros[choice-1]['url']

    while 1:

        print("-------------------------")
        print("1) Realizar o autoagendamento")
        print("2) Informação do Agendamento")
        print("3) Sair")

        option = input("Opção: ")

        if option == "1":
            cc = int(input("Insira o numero do cartão de cidadão: "))

            request = {"cc": cc}
            response = requests.get(f"{centerURL}getAgendamento",
                                    params=request)
            if response.text != "":
                print("Já existe um agendamento com este CC")
                exit(1)

            nome = input("Insira o nome: ")
            idade = int(input("Insira a idade: "))
            email = input("Insira o email: ")
            data = input("Insira a data desejada (dd-mm-yyyy): ")
            date = timeparser.parse(data, dayfirst=True).date().isoformat()
            request = {"cc": cc, "nome": nome, "idade": idade,
                       "email": email, "data": date}
            response = requests.post(f"{centerURL}autoAgendamento",
                                     json=request)
            print("Foi realizado um agendamento com sucesso!")

        elif option == "2":
            cc = int((input("Insira o numero do cartão de cidadão: ")))
            requestUrl = centerURL + "getAgendamentoStatus"
            params = {"cc": cc}
            response = requests.get(requestUrl, params=params)

            if response.text == "Not Found":
                print("Não existe um agendamento para esse \
                       número do cartão de cidadão")
            elif response.text == "null":
                print("O seu agendamento ainda se encontra pendente!")
            elif response.text == "true":
                print("O seu agendamento encontra-se confirmado!")
            elif response.text == "false":
                print("É necessário proceder à remarcação do agendamento!")
                print("Deseja proceder à remarcação?")
                print("s) Proceder ao reagendamento\nn) \
                       Voltar ao menu anterior")
                option = input("Opção: ")

                if option == "s":
                    data = input("Insira a data desejada (dd-mm-yyyy): ")
                    date = timeparser.parse(data, dayfirst=True) \
                        .date().isoformat()
                    requestUrl = centerURL + "updateAgendamento"
                    params = {"cc": cc, "data": date}
                    response = requests.put(requestUrl, params=params)
                    print("Reagendamento realizado com sucesso!")
            else:
                print("Resposta inválida")

        elif option == "3":
            exit(0)

        else:
            print("Opção Invalida")


def centro():
    option = 0

    print("--------Centro-Vacinação--------")

    centros = []
    response = requests.get(f"{DGSURL}getCentros")

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

        if option == "1":
            response = requests.get(centerURL+"getAgendamentos", params=params)
            print(response.json())

        elif option == "2":
            
            response = requests.get(centerURL+"getStocks", params=params)
            print(response.json())
            
            print(response.text)

        elif option == "3":
            cc = int((input("Insira o numero do cartão de cidadão: ")))
            tipo = input("Insira o tipo da vacina: ")

            #tipo de vacina

            params = {"tipoVacina": tipo}
            response = requests.post(f"{centerURL}setVacinado/{cc}",
                                     params=params)
            print(response.text)

        elif option == "4":
            break

        else:
            print("Opção Invalida")


def dgs():
    option = 0

    print("--------Módulo Central (DGS)--------")
    while 1:

        print("1) Fornecer Vacinas")
        print("2) Relatorio")
        print("3) Exit")

        option = input("Option: ")

        if option == "1":
            n_vacinas = int(input("Insira o numero de vacinas: "))
            data = input("Insira a data: ")
            tipo = input("Insira o tipo da vacina: ")

            #tipo de vacina

            params = {"data": data, "n_vacinas": n_vacinas, "tipo": tipo}
            response = requests.get(f"{DGSURL}fornecerVacinas", params=params)
            print(response.text)

        elif option == "2":
            data = input("Insira o dia: ")
            requestUrl = f"{DGSURL}nTotalVacinas"
            params = {"data": data}
            response = requests.get(requestUrl, params=params)
            print(response.json())

        elif option == "3":
            break

        else:
            print("Opção Invalida")


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("-cliente", "--cliente", action='store_true',
                        dest='cliente', help='Menu de cliente', default=False)
    parser.add_argument("-centro", "--centro", action='store_true',
                        dest='centro', help='Menu para gerir o \
                        Centro de Vacinação', default=False)
    parser.add_argument("-dgs", "--dgs", action='store_true',
                        dest='dgs', help='Menu para gerir o \
                        módulo da DGS', default=False)
    results = parser.parse_args()

    if results.cliente:
        cliente()
    elif results.centro:
        centro()
    elif results.dgs:
        dgs()
    else:
        cliente()

import requests

def startUI():
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
    startUI()
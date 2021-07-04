import requests
response = requests.get("http://localhost:8080/api/v1/getCentros")

centros = []

for item in response.json():
    centros.append(item)

choice = 0
for centro in centros:
    print(f"{choice}-> {centro['nome']}")
    choice += 1

params = {"nome": "Centro de Vacinação do João Pavia Pereira"}

centro = requests.get("http://localhost:8080/api/v1/getCentro", params=params)

print(centro.json())

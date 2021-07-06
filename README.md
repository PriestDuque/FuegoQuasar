Operacion Fuego Quasar
==============================================

La aplicacion descifra un mensaje y ubicacion con la informacion que le suministran 3 satelites que tenemos ubicados, con nombres kenobi, skywalker y sato.

URLs ejecución API

a.	Para nivel 2: https://exzzpct2vj.execute-api.us-east-2.amazonaws.com/Prod/topsecret
Se debe ejecutar como POST y con un json de entrada con el formato solicitado, ejemplo:
{
  "satelites": [
    {
      "name": "kenobi",
      "distance": 1800,
      "message": [
        "este",
        "",
        "",
        "mensaje",
        ""
      ]
    },
    {
      "name": "skywalker",
      "distance": 1000,
      "message": [
        "",
        "es",
        "",
        "",
        "secreto"
      ]
    },
    {
      "name": "sato",
      "distance": 632.46,
      "message": [
        "este",
        "",
        "un",
        "",
        ""
      ]
    }
  ]
}

b.	Para nivel 3 se debe solicitar a la misma url solo cambia el satelita al final:
c.	https://exzzpct2vj.execute-api.us-east-2.amazonaws.com/Prod/topsecret-split/kenobi
d.	https://exzzpct2vj.execute-api.us-east-2.amazonaws.com/Prod/topsecret-split/skywalker
https://exzzpct2vj.execute-api.us-east-2.amazonaws.com/Prod/topsecret-split/sato
en cada uno se debe ejecutar como POST y con un json de entrada en el formato solicitado, ejemplo:
{
    "distance": 100,
      "message": [
        "este",
        "",
        "",
        "mensaje",
        ""
      ]
}
	   Luego se debe solicitar la respuesta a la url con GET
      https://exzzpct2vj.execute-api.us-east-2.amazonaws.com/Prod/topsecret-split

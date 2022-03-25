
const url = "http://localhost:8888/api/advert/new"


async function sendData() {
    let imageInput = $('#photo')[0].files[0]
    let image
    if (imageInput !== undefined) {
        image = imageToBinary(imageInput)
    }
    let images  =[]

    let pic = {
        picture:image
    }
    images.push(pic)
    let advert = {

        images : images,
        name: $('#Name').val(),
        description: $('#Description').val(),
        price: $('#Price').val(),
        email: $('#Contacts').val()

    }
    console.log(advert)
    let request = new Request(url, {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(advert),
    })
        console.log(JSON.stringify(advert))
            fetch(request)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(e => console.error(e))

    Swal.fire({
        icon: 'success',
        title: 'Спасибо',
        text: 'Ваше объявление отправлено на модерацию',
        confirmButtonText: '<a style="text-decoration: none; color: white" href="/">На главную</a>'
}
    )

}
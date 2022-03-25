$(async function () {
    await getUnmoderatedAdverts();
    await getUnmoderatedAdvertsCountAndSetBadge();
})

const advertUrl = "http://" + window.location.hostname + ":8888/moderator/api/adverts";
const advertFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getUnmoderatedAdverts: async () => await fetch(`${advertUrl}/getUnmoderatedAdverts`),
    getOneUnmoderatedAdvert: async (id) => await fetch(`${advertUrl}/getOneUnmoderatedAdvert/${id}`),
    updateAdvert: async (advert) => await fetch(`${advertUrl}/editAdvert`, {
        method: 'PUT',
        headers: advertFetchService.head,
        body: JSON.stringify(advert)
    }),
    getUnmoderatedAdvertsCounter: async () => await fetch(`${advertUrl}/getUnmoderatedAdvertsCount`)
}

async function getUnmoderatedAdverts() {
    let advertCards = $('#advertsCards')
    await advertFetchService.getUnmoderatedAdverts()
        .then(res => res.json())
        .then(adverts => {
                if (adverts.length === 0) {
                    advertCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyAdvertList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Объявлений для модерации нет!</h5>                        
                            </p>
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                } else {
                    adverts.forEach(advert => {
                        console.log(advert)
                        let cardFilling =
                            `<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="advertCard${advert.id}">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Наименование объявления: </b>${advert.name}</h5>
                           <p class="card-text" id="cardText"><b>Описание объявления: </b>${advert.description}
                            <br>
                           <b>Email: </b> ${advert.email}
                           <br>
                           <b>Цена: </b> ${advert.price}
                           </p>

                           <button data-advertid="${advert.id}" data-action="accept" class="btn btn-success">Одобрить</a>
                           <button data-advertid="${advert.id}" data-action="decline" class="btn btn-danger">Отклонить</button>

                        </div>
                        <img class="col-sm-6 text-right" src="data:image/jpg;base64, ${advert.images[0].picture}"/>
                    </div>
                </div>
                </div>
                </div>
                <br>`
                        advertCards.append(cardFilling);
                    })
                    getUnmoderatedAdvertsCountAndSetBadge();
                }
            }
        )
    $("#advertsCards").find('button').on('click', (event) => {
        let targetButton = $(event.target);
        let buttonAdvertId = targetButton.attr('data-advertid');
        let buttonAction = targetButton.attr('data-action');
        if (buttonAction === "accept") {
            acceptAdvert(buttonAdvertId);
        } else if (buttonAction === "decline") {
            declineAdvert(buttonAdvertId);
        }
    })
}

async function acceptAdvert(id) {
    await advertFetchService.getOneUnmoderatedAdvert(id)
        .then(res => res.json())
        .then(async advert => {
            advert.moderated = true;
            advert.moderateAccept = true;
            console.log(advert);
            const response = await advertFetchService.updateAdvert(advert);
            if (response.ok) {
                let advertCards = $('#advertsCards')
                advertCards.find(`#advertCard${advert.id}`).remove() //!
                await advertFetchService.getUnmoderatedAdverts()
                    .then(res => res.json())
                    .then(adverts => {
                        if (adverts.length === 0) {
                            advertCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyAdvertList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Объявлений для модерации нет!</h5>                        
                            </p>
                  
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                        }
                    })
                await getUnmoderatedAdvertsCountAndSetBadge();
            } else {
                console.log("Произошла ошибка")
            }
        })
}

async function declineAdvert(id) {
    let form;
    let cardField;
    if (!(document.getElementById(`declineAdvert${id}`))) {
        await advertFetchService.getOneUnmoderatedAdvert(id)
            .then(res => res.json())
            .then(advert => {
                    if (!(document.getElementById(`declineAdvert${advert.id}`))) {
                        form = `<form id="declineAdvert${advert.id}">
                    <input type="text" class="form-control" id="rejectedAdvertReasonTo${advert.id}" name="rejectedReasonTo${advert.id}" placeholder="Введите причину отказа" style="width: 200px; height: 150px;" 
                    min="5">
                    <button type="button" class="btn btn-primary" id="rejectAdvertReason${advert.id}">Отправить</button>\
                    </form>`
                        cardField = $("#advertsCards").find(`#advertCard${advert.id}`);
                        cardField.append(form);
                        console.log("Created form")
                    }
                    $(`#declineAdvert${advert.id}`).find('button').on('click', () => {
                        advert.moderated = true;
                        advert.moderateAccept = false;
                        advert.moderatedRejectReason = document.querySelector(`#rejectedAdvertReasonTo${advert.id}`).value;
                        sendAdvertAndDeleteCard(advert);
                    })
                }
            )
    }
}

async function sendAdvertAndDeleteCard(advert) {
    const response = await advertFetchService.updateAdvert(advert);
    if (response.ok) {
        let advertCards = $('#advertsCards')
        advertCards.find(`#advertCard${advert.id}`).remove()
        await advertFetchService.getUnmoderatedAdverts()
            .then(res => res.json())
            .then(adverts => {
                if (adverts.length === 0) {
                    advertCards.append(`<div class="row">
                         <div class="col-sm-10">
                            <div class="card" id="emptyAdvertList">
                                <div class="row card-body">
                                    <div class="col-sm-10">
                          <h5 class="card-title"><b>Объявлений для модерации нет!</h5>                        
                            </p>                  
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                }
                getUnmoderatedAdvertsCountAndSetBadge();
            })
    } else {
        console.log("Произошла ошибка")
    }
}

async function getUnmoderatedAdvertsCountAndSetBadge() {
    let precounter = await advertFetchService.getUnmoderatedAdvertsCounter();
    let counter = precounter.json();
    counter.then(counter => {
        $("#v-pills-tab #v-pills-adverts-tab").html(`Объявления <span
                        class="badge badge-secondary">${counter}</span>
                    <span class="sr-only">unmoderated advert</span></a>`)
    })
}
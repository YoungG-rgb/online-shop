function search(items, searchInput) {
    let logs = ``;

    let isEmpty = true;
    items.forEach((i) => {
        if (i.name.toLowerCase().includes(searchInput.value.toLowerCase()) || searchInput.value === '') {
            isEmpty = false;
            logs += `<div class="item-div">
                         <a href="/item/${i.id}"><img src="data:image/png;base64,${i.images[0].picture}" class="img-thumbnail"></a>
                         <h6>${i.name}</h6>
                         <h6>${i.price}</h6>
                         <p>${i.description}</p>
                         <button type="button" class="btn btn-primary basket-plus-div" id="${i.id}">В корзину</button>
                         </div>`;
        }
    })

    // Проверка что нету предметов
    if (isEmpty) {
        logs += `<h4>Ничего не найдено</h4>`
    }
    return logs;
}
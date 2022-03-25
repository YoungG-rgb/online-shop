let id = $('.user-id').val();
document.getElementById('secondDate').valueAsDate = new Date();

fetch(`/getOrders/${id}`)
    .then(res => res.json())
    .then(orders => {
        let tempOrder = "";
        orders.forEach((o) => {
            tempOrder += `
             <tr>
               <td>${o.id}</td>
            <td>${o.date}</td>
               <td>${o.total}</td>
               <td>${o.itemCost}</td>
               <td>${o.total - o.itemCost}</td>
             </tr>`;
            document.querySelector("#userOrders").innerHTML = tempOrder;
        })
    })

fetch(`/getUserSalesItems/${id}`)
    .then(res => res.json())
    .then(items => {
        let tempItems = "";
        items.forEach((i) => {
            tempItems += `
             <tr>
                <td>${i.id}</td>
                <td>${i.name}</td>
                <td>${i.count}</td>
                <td>${i.price}</td>
                <td></td>
             <tr>`;
            document.querySelector("#userItems").innerHTML = tempItems;
            select.append(new Option(i.name, i.id));
        })
    })

document.body.onload = function () {
    document.getElementById('getReport').onclick = function () {

        fetch(`/getUserSalesItems/${id}`)
            .then(res => res.json())
            .then(items => {
                let tempItems = "";
                let selectArr = $('#select').val().toString()
                items.forEach((i) => {
                    if (selectArr.includes(i.id))
                        tempItems += `
             <tr>
                <td>${i.id}</td>
                <td>${i.name}</td>
                <td>${i.count}</td>
                <td>${i.price}</td>
                <td></td>
             <tr>`;
                    document.querySelector("#userItems").innerHTML = tempItems;
                })
            })

        fetch(`/getOrders/${id}`)
            .then(res => res.json())
            .then(orders => {
                let tempOrder = "";
                let firstDate = $('#firstDate').val().split('-').join('')
                let secondDate = $('#secondDate').val().split('-').join('')
                orders.forEach((o) => {
                    if (firstDate <= o.date.substring(0, 10).split('-').join('') && o.date.substring(0, 10).split('-').join('') <= secondDate)
                        tempOrder += `
             <tr>
               <td>${o.id}</td>
            <td>${o.date}</td>
               <td>${o.total}</td>
               <td>${o.itemCost}</td>
               <td>${o.total - o.itemCost}</td>
             </tr>`;
                    document.querySelector("#userOrders").innerHTML = tempOrder;
                })
            })
    }
}

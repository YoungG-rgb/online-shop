let key = 11;
let nullDate = new Date(null) // for if
function createCard(coupon){
    let card = `<div class="card text-dark bg-light mb-3 text-center">`;
        card += `<div class="card-header"><h5>#${coupon.id * key}</h5></div>`;
        card += `<div class="card-body">`;
        card += `<div class="card-text">`;
        card += `<p><strong>Срок действия - До: </strong>` + getNormalDate(new Date(coupon.end)) + `</p>`;
        card += `<p><strong>Магазин:</strong> ` + coupon.shop.name + `</p>`;
        card += `<p><strong>Статус купона:</strong> ` + coupon.status + `</p>`;
        card += `<p><strong>Купон на сумму:</strong> ` + coupon.sum + `</p>`;
        card += `</div></div></div>`;
    return card;
}
//----------------------------------------------------------------------------------------------------------------------
async function showCoupons(){
    let actual = "";
    let overdue = "";
    let used = "";
    let all = "";
    await fetch("http://localhost:8888/getUser")
        .then(res => res.json())
        .then(user => {
            let now = new Date();
            for (let i = 0; i < user.coupons.length; i++) {
                 all += createCard(user.coupons[i]);
                 if (user.coupons[i].status === "ACTUAL") {
                     let date = new Date(user.coupons[i].end)
                     if (nullDate.getFullYear() === user.coupons[i].end().getFullYear()){
                         updateToOverdue(user.coupons[i])
                     } else if (now.getFullYear() >= date.getFullYear()) {
                        if(now.getMonth() >= date.getMonth()) {
                            if(now.getDay() > date.getDay()) {
                                updateToOverdue(user.coupons[i])
                            }
                        }
                    }
                 actual += createCard(user.coupons[i]);
                 } else if (user.coupons[i].status === "USED") {
                     used += createCard(user.coupons[i]);
                 } else if (user.coupons[i].status === "OVERDUE") {
                     overdue += createCard(user.coupons[i]);
                 }
            }
            document.querySelector(".allCoupons").innerHTML = all;
            document.querySelector(".actualCoupons").innerHTML = actual;
            document.querySelector(".usedCoupons").innerHTML = used;
            document.querySelector(".overdueCoupons").innerHTML = overdue;
        })
}
showCoupons();
//----------------------------------------------------------------------------------------------------------------------
async function updateToOverdue(coupon){
    await fetch("http://localhost:8888/api/coupon/update/overdue" + coupon.id, {
        method: 'PUT',
        body: JSON.stringify(coupon),
        headers: { "Content-Type": "application/json; charset=utf-8" }
    }).then(res => {console.log(res)})
}
function getNormalDate(date){
    return date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear();
}
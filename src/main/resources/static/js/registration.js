const url = 'http://localhost:8888'
const userService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    registerNewUser: async (user) => await fetch('/registration', {
        method: 'POST',
        headers: userService.head,
        body: JSON.stringify(user)
    }),
}

    $('#registrationButtonModal').on('click', function () {
        $('#staticBackdrop').slideUp()
        $('.registrationForm').slideDown()
    })
    $('.btn-close').on('click', function () {
        $('.registrationForm').slideUp()
        $('#staticBackdrop').slideDown()
    })

function sendRegistration() {
    let genderCheck = document.getElementsByClassName('form-check-input')
    let gender;
    for (let i = 0; i < genderCheck.length; i++) {
        if (genderCheck[i].checked) {
            gender = genderCheck[i].value
            i = genderCheck.length
        }
    }
    let imageInput = $('#reg-avatar')[0].files[0]
    let image
    if (imageInput !== undefined) {
        image = imageToBinary(imageInput)
    }
    let user = {
        email: $('#reg-email').val(),
        username: $('#reg-username').val(),
        password: $('#reg-password').val(),
        phone: $('#reg-phoneNumber').val(),
        firstName: $('#reg-firstName').val(),
        lastName: $('#reg-lastName').val(),
        gender: gender,
        birthday: $('#reg-birthday').val(),
        images: {
            picture: image,
            isMain: true
        }
    }
    userVerification(user)
}
//check user to exists in BD and validation user fields
async function userVerification(user) {
    $('.alert').hide("slide")
    let response = await userService.registerNewUser(user)
    if (response.ok) {
        $('#successfulRegistration').show('slide')
    } else {
        let responseBody = await response.json().then(result => result)
        if (responseBody.isExist !== undefined) {
            let massage = responseBody.isExist;
            let alert = $('#isExistAlert')
            alert.empty()
            alert.append(massage)
            alert.show('slide')
        } else {
            responseBody = responseBody.errors
            let alert = $('#isErrors')
            alert.empty()
            for(let i in responseBody) {
                alert.append(responseBody[i] + '; ')
            }
            alert.show('slide')
        }
    }
}

// convert image to byte array
function imageToBinary(image) {
    let reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onloadend = function () {
        let data = (reader.result).split(',')[1];
        let binaryBlob = atob(data);
        localStorage.setItem("image", binaryBlob)
    }
    let imageBase64 = localStorage.getItem("image")
    return base64ToBinary(imageBase64)
}

function base64ToBinary(imageBase64) {
    let bytes = [];
    for (let i = 0; i < imageBase64.length; i++) {
        bytes.push(
            imageBase64.charCodeAt(i)
        );
    }
    return bytes
}

//form-login
function login_btn_click(){
    $(document).on("click", ".login-btn", function (){
        document.querySelector(".login-content").innerHTML = form_login;
        $(".login-form").show();
    })
}
login_btn_click();

//form-registration
function registration_click(){
    $(document).on("click", ".input-button-registration", function (){
        document.body.style.overflow = "hidden";
        document.querySelector(".login-content").innerHTML = form_registration;
    })
}
registration_click();

function close_btn_click(){
    $(document).on("click", ".btn-close", function (){
        $(".login-form").hide();
        document.body.style.overflow = "visible";
    })
    $(document).on("click", ".login-form", function (e){
        if($(e.target).hasClass("login-form")){
            $(".login-form").hide();
            document.body.style.overflow = "visible";
        }
    })
}
close_btn_click();

// проверка на аутентификацию
function sendLogin() {
    let user = {
        username: $('#SignInUsername').val(),
        password: $('#SignInPassword').val()
    }
    if (user.username === "" || user.password === "") {
        $('#ifError').hide()
        $('#ifEmpty').show()
    } else {
        fetch(url + '/login', {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    window.location.replace(response.url)
                } else {
                    document.getElementById('ifError').innerText = `О нет, чувак, логин или пароль введены неверно (или еще что-то пошло не так). Вот код статуса ответа: ${response.status}, разбирайся.`
                    $('#ifEmpty').hide()
                    $('#ifError').show()
                }
            })
            .catch(e => {
                document.getElementById('ifError').innerText = "А это окно говорит, что что-то посерьезней случилось. Консоль почитай."
                $('#ifError').show()
                console.error(e)
            })
    }
}

//form login
let form_login = `<div class="modal-dialog">
                    <div class="modal-content">
                        <div style="text-align: right">
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-header">
                            <div class="image-header">
                                  <img src="https://covr.org/wp-content/uploads/Amazon-swoop-145x100.jpg"
                                       alt="https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/172/69ae45600772983179fe0d89b3f3cc1b_crop.jpeg">
                              </div>
                        </div>
                        <div class="alert alert-danger" role="alert" style="display: none" id="ifError">
                            Введены неправильные данные!
                        </div>
                        <div class="alert alert-warning" role="alert" style="display: none" id="ifEmpty">
                            Не не не, заполняем поля, пожалуйста!
                        </div>
                        <div class="modal-body">
                                <div class="mb-3">
                                    <label for="SignInUsername" class="form-label">Имя пользователя</label>
                                    <input type="text" placeholder="Username" class="form-control"
                                           id="SignInUsername" name="username">
                                </div>
                                <div class="mb-3">
                                    <label for="SignInPassword" class="form-label">Пароль</label>
                                    <input type="password" placeholder="Password" class="form-control"
                                           id="SignInPassword" name="password">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="flexCheckDefault"
                                               name="remember-me">
                                        <label class="form-check-label" for="flexCheckDefault">
                                            Запомнить пароль
                                        </label>
                                    </div>
                                    <div class="button-in">
                                        <button type="submit" class="btn btn-warning" onclick="sendLogin()">Войти</button>
                                    </div>
                                </div>
                        </div>
                        <div class="modal-footer">
                            <a id="registrationButtonModal" class="input-button-registration">Зарегистрироваться</a>
                        </div>
                    </div>
                  </div>`

//form registration
let form_registration = `<!--<div class="modal registrationForm">-->
<!--    <div class=" mx-auto col-lg-4">-->
        <form class="p-4 p-md-5 border rounded-3 bg-light" id="registrationForm">
            <div style="text-align: right">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="form-floating my-3" id="email">
                <input type="email" class="form-control email" name="email" id="reg-email">
                <label for="email">Email address</label>
                <div class="alert alert-danger collapse" role="alert" id="emailAlert">
                </div>
            </div>
            <div class="form-floating mb-3" id="username">
                <input class="form-control username" name="username" id="reg-username">
                <label for="username">User Name</label>
                <div class="alert alert-danger collapse" role="alert" id="usernameAlert">
                </div>
            </div>
            <div class="form-floating mb-3">
                <input class="form-control firstName" name="firstName" id="reg-firstName">
                <label for="firstName">First Name</label>
                <div class="alert alert-danger collapse" role="alert" id="firstNameAlert">
                </div>
            </div>
            <div class="form-floating mb-3">
                <input class="form-control lastName" name="lastName" id="reg-lastName">
                <label for="lastName">Last Name</label>
                <div class="alert alert-danger collapse" role="alert" id="lastNameAlert">
                </div>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control password" name="password" id="reg-password">
                <label for="password">Password</label>
                <div class="alert alert-danger collapse" role="alert" id="passwordAlert">
                </div>
            </div>
            <div class="form-floating mb-2">
                <input type="tel" class="form-control phone" value="+7" name="phoneNumber" id="reg-phoneNumber">
                <label for="phone">Phone Number</label>
                <div class="alert alert-danger collapse" role="alert" id="phoneAlert">
                </div>
            </div>
            <h6 class="mb-2 pb-1 gender-label">Gender: </h6>
            <div class="my-3" id="gender-check">
                <div class="form-check">
                    <input id="male" name="gender" type="radio" class="form-check-input" required="" value="MALE"
                           checked>
                    <label class="form-check-label" for="male">Male</label>
                </div>
                <div class="form-check">
                    <input id="female" name="gender" type="radio" class="form-check-input" required=""
                           value="FEMALE">
                    <label class="form-check-label" for="female">Female</label>
                </div>
                <div class="form-check">
                    <input id="UNKNOWN" name="gender" type="radio" class="form-check-input" required=""
                           value="UNKNOWN">
                    <label class="form-check-label" for="UNKNOWN">Other</label>
                </div>
            </div>            
            <div class="form-floating mb-3">
                <input type="date" class="form-control birthday" name="birthday" id="reg-birthday">
                <label for="birthday">Date of birth:</label>
                <div class="alert alert-danger collapse" role="alert" id="birthdayAlert">
                </div>
            </div>
            <div class="mb-3">
                <label for="avatar" class="form-label">User avatar</label>
                <input class="form-control avatarImage" accept="image/*" type="file" name="avatar" id="reg-avatar">
            </div>
            <button class="w-100 btn btn-lg btn-warning registrationButton" type="button" id="registrationButton" name="registrationButton"
                    onclick="sendRegistration()">
                Регистрация
            </button>
            <div class="alert alert-danger collapse" role="alert" id="isExistAlert">
            </div>
            <div class="alert alert-danger collapse" role="alert" id="isErrors">
            </div>
            <div class="alert alert-success collapse" role="alert" id="successfulRegistration">
                Заявка подана, для активации аккаунта мы отправили ссылку на Вашу почту.
            </div>
        </form>
<!--    </div>-->
<!--</div>-->`




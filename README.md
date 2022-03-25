# Platform

## Database description (refactored version)
ERD
![](src\main\resources\static\images\erd\ERD.png)

***
### Описания сущностей

#### 1. Address
Name|Description
---|---|
id| уникальный номер адреса
country| номер страны
city| номер города
street| улица
house| дом

_Адрес пользователя, связь с User ManyToMany_

***
#### 2.  CartItem
Name|Description
---|---|
id| уникальный номер товара, добавленного в корзину
quanity| количество
item| номер товара
shop| номер магазина
user| номер юзера

_Товар, добавленный в корзину_

***
#### 3. Category
Name|Description
---|---|
id| уникальный номер категории
name| имя категории

_Категория товара, товар может ссылаться на несколько категорий_

***
#### 4. Chat
Name|Description
---|---|
id| уникальный номер чата
hash| хэшномер

_Чат пользователей, связан с пользователями и сообщениями OneToMany_

***
#### 5. City
Name|Description
---|---|
id| уникальный номер города
name| название города
cityIndex| индекс города

***
#### 6. Country
Name|Description
---|---|
id| уникальный номер страны
name|название страны
***

#### 7. Coupon
Name|Description
---|---|
id| уникальный номер купона

***
#### 8. Discount
Name|Description
---|---|
id| уникальный номер скидки
minOrder| сумма минимального заказа
percentage| процент скидки
fixedDiscount| фиксированная скидка

_Скидка, назначаемая пользователю магазином_

***
#### 9. Image
Name|Description
---|---|
id| уникальный номер изображения
picture| байтовый массив
isMain| флаг отображения изображения как главного

_Изображения товаром, аватары пользователей, логотипы магазинов_

***
#### 10. Item
Name|Description
---|---|
id| уникальный номер товара
shop| номер магазина товара
name| название
price| цена
count| количество в магазине
rating| рейтинг
description| описание
discount| скидка
isModerated|
isModerateAccept|
moderatedRejectReason|
isPretendentToBeDeleted|

_Товар, размещаемый магазином_

***
#### 11. Message
Name|Description
---|---|
id| уникальный номер сообщения
chat| номер чата
to| номер получателя
from| номер отправителя
textMessage| текст сообщения
viewed| флаг просмотра

_Сообщения в чатах между пользователями_

***
#### 12. Order
Name|Description
---|---|
id| уникальный номер заказа
date| дата заказа
status| статус заказа
total| суммарная сумма
orderDetails| номер сущности, содержащей подробности заказа
user| номер заказавшего пользователя

_Заказы пользователей, связаны с Item ManyToMany_

***
#### 13. OrderDetail
Name|Description
---|---|
id| уникальный номер
currency| валюта
method| метод оплаты
intent|
description| описание заказа
country| страна доставкизаказа
city| город доставки заказа
cityIndex| индекс города
street| улица доставки
house| дом доставки
buyerName| имя получателя
buyerPhone| телефон получателя

_Подробные элементы заказа_

***
#### 14. PersistentLogins
Name|Description
---|---|
id|
series|
username|
token|
lastUsed|

***
#### 15. ReviewShop
Name|Description
---|---|
id| уникальный номер отзыва
user| номер пользователя, оставившего отзыв
shop| номер магазина, на который оставили отзыв
dignity| плюсы
flaw| минусы
text| текст отзыва
date| дата отзыва
rating| рейтинг отзыва
isModerated|
isModerateAccept|
moderatedRejectReason|

_Отзыв пользователя на магазин_

***
#### 16. ReviewShop
Name|Description
---|---|
id| уникальный номер отзыва
user| номер пользователя, оставившего отзыв
item| номер товара, на который оставили отзыв
dignity| плюсы
flaw| минусы
text| текст отзыва
date| дата отзыва
rating| рейтинг отзыва
isModerated|
isModerateAccept|
moderatedRejectReason|

_Отзыв пользователя на товар_

***
#### 17. Role
Name|Description
---|---|
id| уникальный номер
name| имя роли

_Определение прав пользователей в системе_

***
#### 18. Shop
| Name                    | Description                                                                                                                                                              |
|-------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| id                      | уникальный номер магазина                                                                                                                                                |
| name                    | название                                                                                                                                                                 |
| email                   | почта м                                                                                                                                                                  |
| phone                   | телефон                                                                                                                                                                  |
| description             | описание                                                                                                                                                                 |
| city                    | номер города магазина                                                                                                                                                    |
| image                   | номер изображения логотипа магазина                                                                                                                                      |
| count                   |                                                                                                                                                                          |
| rating                  | рейтинг                                                                                                                                                                  |
| user                    | номер пользователя - владельца магазина                                                                                                                                  |
| isModerated             | находится на модерации<br/> true виден админу и модератору, но не виден остальным юзерам.<br/> false прошел модерацию. Виден всем юзерам. Из раздела модерации удаляется |
| isModerateAccept        | прошел модерацию<br/> true прошел модерацию. Открыт для публичного доступа.<br/> false не прошел модерацию. Закрыт для публичного доступа                                |
| moderatedRejectReason   | причина отклонения модерации                                                                                                                                             |
| isPretendentToBeDeleted | true владелец поставил метку на удаление.<br/> false метки на удаления нет                                                                                               |

Сущность магазина. Может размещать товары

***
#### 19. User
Name|Description
---|---|
id| уникальный номер пользователя
firstName| имя
lastName| фамилия
email| почта
phone| телефон
username| ник
password| пароль
age| возраст
gender| пол
birthday| дата рождения
image| номер изображения-аватара
activate| флаг активации пользователя
activationCode| код активации

_Сущность пользователя. Может заказывать товары или создавать магазины и размещать там новые товары_
***
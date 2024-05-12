function addToCart(id)
{
    let productImg = document.getElementById("img" + id);
    let imgSrc = productImg.getAttribute("src");
    let imgSrcString = String(imgSrc);
    let productName = document.getElementById("title" + id).innerText;
    let productPrice = document.getElementById("info" + id).innerText;
    productPrice = parseInt(productPrice.replaceAll(' ', '').slice(0, -4));
    let item = {
        id: id,
        img: imgSrcString,
        name: productName,
        price: productPrice,
    };
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let existingItem = cart.find(cartItem => cartItem.name === item.name);

        cart.push(item); //Добавляем новый товар
    localStorage.setItem('cart', JSON.stringify(cart)); //сохранямм новй товар в локальном хранилище
}

// Получаем кнопку открытия корзины
var oformitButton = document.getElementById("Oformit_but");

// Получаем модальное окно и кнопку закрытия
var cartModal = document.getElementById("cartModal");
var closeButton = document.getElementsByClassName("close")[0];

function fillCartItems()
{
    let cartItemsElement = document.getElementById("cart-items");
    cartItemsElement.innerHTML = ""; //Очищает содержимое элемента корзины перед заполнением новыми товарами
    let summ = 0; // summ для хранения общей суммы стоимости товаров в корзине
    let cart = JSON.parse(localStorage.getItem('cart')) || []; //Получает текущий список товаров в корзине из локального хранилища браузера
    if (cart.length == 0){ //Если корзина пуста, перенаправляет пользователя на страницу каталога
        // Привязываем обработчик события на кнопку открытия корзины
        oformitButton.addEventListener("click", function(event)
        {
            event.preventDefault(); // Предотвращаем отправку формы
            cartModal.style.display = "block"; // Показываем модальное окно при клике
        });

        // Привязываем обработчик события на кнопку закрытия
        closeButton.addEventListener("click", function()
        {
            cartModal.style.display = "none"; // Скрываем модальное окно при клике на крестик
        });

        // Закрываем модальное окно, если пользователь кликает вне его области
        window.addEventListener("click", function(event)
        {
            if (event.target == cartModal) {
                cartModal.style.display = "none";
            }
        });
        // window.location.href = 'catalog';
    } else { //Если корзина не пуста
        oformitButton.addEventListener("click", function(event) {
            removeAll(); // Вызываем функцию removeAll()
        });

    }
    for (let ind = 0; ind < cart.length; ind++) { //заполнение скрытых полей формы (cartIndex, cartName, cartCoursPrice) значениями из элементов корзины
        let cartItemElement = document.createElement('div');
        cartItemElement.classList.add('oneGood');
        cartItemElement.innerHTML = `
            <div class="card">
                <img src="${cart[ind].img}" alt="${cart[ind].name}">
                <p>${cart[ind].name}</p>
            </div>
            <p>${cart[ind].price} ₽</p>
            <div class = "removeAndPrice">
                <button class="remove-btn" onclick="removeItem(${ind})"></button>
            </div>`;
        let cartIndex = document.getElementById('index' + ind);
        cartIndex.value = parseInt(cart[ind].id);
        let cartName = document.getElementById('name' + ind);
        cartName.value = cart[ind].name;
        // let cartAmount = document.getElementById('amount' + ind);
        // cartAmount.value = parseInt(cart[ind].amount);
        let cartCoursPrice = document.getElementById('coursPrice' + ind);
        cartCoursPrice.value = parseInt(cart[ind].price);
        // summ += cart[ind].amount * cart[ind].price;
        summ += cart[ind].price;
        cartItemsElement.appendChild(cartItemElement);
    }
    let quantitySumm = document.getElementById(`text2`); //Общая стоимость для всех товаров корзины
    quantitySumm.value = parseInt(quantitySumm.value | 0) + summ;
}

function removeItem(index)
{
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.splice(index, 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    submitForm();
    fillCartItems();
}

function removeAll()
{
    localStorage.removeItem('cart');
    window.location.href = 'account';
}
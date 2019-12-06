import {UserRestPaginationService} from './rest/entities-rest-pagination.js'
// import {users} from "./input-box-at";


const user_service = new UserRestPaginationService();
const modal = document.getElementById('associatedUserList');

$("#form_message_input").on('input', function () {
    let text = document.getElementById('form_message_input').value;
    let sub_user;
    if (text.length === 1 && text === "@"){
        modal.style.display = "block";
    }else if (text.length >=3 && text.slice(0,1) === "@"){
        sub_user = text.slice(1);
    }
    showAllUsers(sub_user)
});

const showAllUsers = (text) => {
    const allUsers = user_service.getAll();
    $('#associatedUserListSelect')
        .find('option')
        .remove()
        .end();

    if (!text) {
        allUsers.then(response => {           //После того как Юзеры будут получены, начнется выполнение этого блока
            $.each(response, (i, item) => {
                $('#associatedUserListSelect')
                    .append(
                        `<option id="atUserSelectOption" class="atUserSelectOption" value="${item.id}">${item.name}</option>`
                    );
            });
        });
    }else{
        allUsers.then(response => {           //После того как Юзеры будут получены, начнется выполнение этого блока
            $.each(response, (i, item) => {
                if ((item.name).includes(text)) {
                    $('#associatedUserListSelect')
                        .append(
                            `<option id="atUserSelectOption" class="atUserSelectOption" value="${item.id}">${item.name}</option>`
                        );
                }
            });
        });
    }
};

$('#associatedUserListSelect').on('change', function () {
    let str = "";
    // For multiple choice
    $("select option:selected").each(function () {
        users.push($(this).val());
        let text = $(this).text();
        if (text !== ' --') {
            str += "@" + text + " ";
        }
    });

    $('#form_message_input').val(str);
    document.getElementById('associatedUserList').style.display = "none";
});

// document.onmouseup(function (e) {
//     if (!modal.is(e.target)){
//         modal.style.display = "none";
//     }
// });
document.addEventListener("mouseup", function (e){
    let block = $('associatedUserList');
    if (!block.is(e.target)){
        modal.style.display = "none";
    }
});
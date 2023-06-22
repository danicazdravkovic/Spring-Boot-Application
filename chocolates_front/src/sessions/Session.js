// https://www.section.io/engineering-education/how-and-when-to-apply-session-storage-with-javascript/#:~:text=Session%20storage%20is%20a%20popular,user%20closes%20the%20browser%20window.

export function store(user,role) { //stores items in the sessionStorage
    // var username = document.getElementById('input_username').value;
    // var password = document.getElementById('input_password').value;
    clearStorage()
    if(role==="admin"){
        const loggedUser = {
            id: user.adminID,
            name: user.name,
            username: user.username,
            password: user.password,
            role:role
        }
    window.sessionStorage.setItem('loggedUser', JSON.stringify(loggedUser));
    // console.log(loggedUser)

    }

    if(role==="customer"){
        const loggedUser = {
            id: user.customerID,
            name: user.name,
            username: user.username,
            password: user.password,
            role:role
        }
    window.sessionStorage.setItem('loggedUser', JSON.stringify(loggedUser));

    // console.log(loggedUser)

    }

    // console.log("In session is stored an object: ")
    //converting object to string
}


export function getLoggedUser(){
    return JSON.parse(window.sessionStorage.getItem("loggedUser"));
}


function clearStorage() { //clears the entire sessionStorage
    sessionStorage.clear();
    // console.log("clear logged user");
}
export function logOutUser(){
    sessionStorage.removeItem('loggedUser');
}

export function storeCustomerItems(items) {
    var userID=getLoggedUser().id
    var key="customer_items_"+userID
    // window.sessionStorage.removeItem(key)

    window.sessionStorage.setItem(key, JSON.stringify(items));
    // console.log(getCustomerItems())
}
export function getCustomerItems() {
    var userID=getLoggedUser().id
    return JSON.parse(window.sessionStorage.getItem("customer_items_"+userID));

}
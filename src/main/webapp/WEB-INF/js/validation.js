function checkForm(form)
{
    if(form.login.value === "") {
        alert("Error: Username cannot be blank!");
        form.login.focus();
        return false;
    }
    re = /^\w+$/;
    if(!re.test(form.login.value)) {
        alert("Error: Username must contain only letters, numbers and underscores!");
        form.login.focus();
        return false;
    }

    if(form.password.value !== "") {
        if(form.password.value.length < 5 || form.password.value.length > 16 ) {
            alert("Error: Incorrect password length!");
            form.password.focus();
            return false;
        }

    } else {
        alert("Error: Please check that you've entered and confirmed your password!");
        form.password.focus();
        return false;
    }

    return true;
}
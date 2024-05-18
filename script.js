var access_token = "";

var user_id = 0;

function index() {
    const loaderContainer = document.getElementById('loaderContainer');
    const errorConnection = document.getElementById('errorConnection');
    const bodyClass = document.getElementById('bodyClass');

    loaderContainer.style.display = 'block';

    setTimeout(() => {
        API_index().then(isConnected => {
            if (isConnected) {
                loaderContainer.style.display = 'none';
                errorConnection.style.display = 'none';
                bodyClass.style.display = 'block';
            } else {
                loaderContainer.style.display = 'none';
                errorConnection.style.display = 'block';
                bodyClass.style.display = 'none';
            }
        });    
    }, 3000);
}

function login() {
    const url = new URL(window.location.href);

    const params = new URLSearchParams(url.search);

    var user = "", pass = "";

    for (const [key, value] of params.entries()) {
        if (key == "email") {
            user = value;
        } else if (key == "password") {
            pass = value;
        }
    }

    if (user.length > 0 && pass.length > 0) {
        var userDetails = API_login(user, pass);
        document.cookie = `userDetails=${encodeURIComponent(userDetails)}; path=/; domain=cyberblog.click; SameSite=None; Secure; max-age=${60 * 60 * 24 * 365};`;
        window.location.href = "/";
    } else {
        return;
    }
}

function showSettings() {
    const settingsPage = document.getElementById('settingsPage');
    const algorithmPage = document.getElementById('algorithmContent');
    const startup = document.getElementById('startup');
    const saveButton = document.getElementById('saveButton');
    const cancelButton = document.getElementById('cancelButton');
    const btnBack = document.getElementById('btn-back');

    startup.style.display = 'none';
    algorithmPage.style.display = 'none';
    settingsPage.style.display = 'block';
    saveButton.className = 'btn btn-primary';
    cancelButton.className = 'btn btn-danger';
    btnBack.className = 'btn btn-link btn-transparent';
}

function hideSettings() {
    const settingsPage = document.getElementById('settingsPage');
    const startup = document.getElementById('startup');
    const saveButton = document.getElementById('saveButton');
    const cancelButton = document.getElementById('cancelButton');
    const btnBack = document.getElementById('btn-back');

    settingsPage.style.display = 'none';
    startup.style.display = 'block';
    saveButton.className = '';
    cancelButton.className = '';
    btnBack.className = 'btn btn-link btn-transparent';

    window.location.href = "#";
}

var id = 0;

function loadUser() {
    API_getCurrentUser().then(ret => {
        id = ret.id;

        const oldNavBar = document.querySelector('#account_management');
        const username = document.getElementById('username');
        const password = document.getElementById('password');
        const fullname = document.getElementById('fullname');
        const email = document.getElementById('email');

        if (oldNavBar) {
            const newNavBar = `
    <ul class="navbar-nav">
                    <li class="nav-item">
                        <a id="login" class="nav-link transition" href="#settingsPage" onclick=\"showSettings();\">Chào mừng, ${ret.fullname}</a>
                    </li>
                    <li class="nav-item">
                        <a id="logout" class="nav-link transition" href="#settingsPage" onclick="logout();">Đăng xuất</a>
                    </li>
                </ul>`;
                oldNavBar.outerHTML = newNavBar;
        } else {
            console.error("Server Internal Error (500)");
        }

        username.value = ret.username;
        password.value = "";
        fullname.value = ret.fullname;
        email.value = ret.email;
    }).catch(error => {
        console.error(error);
    });
}

function handleClick(id) {
    API_DisplayAlgorithms().then(ret => {
        var index = 0;

        for (var i = 0; i < ret.length; i++) {
            if (ret[i].id == id) {
                index = i;
                break;
            }
        }

        const title = document.querySelector('#name');
        title.innerHTML = '<h1>' + (ret[index].title.replace(/\n/g, '<br>') ? ret[index].title.replace(/\n/g, 'br') : 'Thuật toán không xác định') + '</h1>';

        const def = document.querySelector('#definition');
        def.innerHTML = '<p>' + (ret[index].algorithmDefinition.replace(/\n/g, '<br>') ? ret[index].algorithmDefinition.replace(/\n/g, '<br>') : 'Thuật toán không có khái niệm') + '</p><br>';

        const work = document.querySelector('#howtowork');
        work.innerHTML = '<p>' + (ret[index].algorithmHowToWork.replace(/\n/g, '<br>') ? ret[index].algorithmHowToWork.replace(/\n/g, '<br>') : 'Thuật toán không có cách sử dụng') + '</p><br>';

        const app = document.querySelector('#app');
        app.innerHTML = '<p>' + ((ret[index].algorithmApplication ? ret[index].algorithmApplication.replace(/\n/g, '<br>') : 'Thuật toán không có ứng dụng') ? ret[index].algorithmApplication.replace(/\n/g, '<br>') : 'Thuật toán không có ứng dụng') + '</p><br>';

        const example = document.querySelector('#ex');
        example.innerHTML = '<div class=\"code-container\"><pre><code class="language-python">' + (ret[index].algorithmExample ? ret[index].algorithmExample : 'Thuật toán không có mã mẫu, mã giả hoặc ví dụ minh họa') + '</code></pre></div>';
        Prism.highlightAll();

        const startup = document.querySelector('.startup');
        startup.style.display = "none";

        const contentAlgorithm = document.querySelector('.algorithmContent');
        contentAlgorithm.style.display = "block";
    });
}

function loadAlgorithms() {
    API_DisplayAlgorithms().then(ret => {
        try {
            const sidebar = document.querySelector('.list-group');
            sidebar.innerHTML = '';
            
            // Assuming ret is an array of algorithms
            ret.forEach(algorithm => {
                // Create a new list item for each algorithm
                const listItem = document.createElement('li');
                listItem.classList.add('list-group-item');

                const itemDiv = document.createElement('div');
                itemDiv.classList.add('item');

                const span = document.createElement('span');
                span.classList.add('label');

                const button = document.createElement('a');
                button.classList.add('btn');
                button.style.width = '12vw';
                // button.style.height = '12vh';
                // button.style.marginLeft = '-1.08vw';
                // button.style.marginTop = '-1.25vh';
                // button.style.marginBottom = '-1.02vh';
                button.style.textAlign = 'center';
                button.style.justifyContent = 'center';
                button.style.padding = '20px';
                button.style.fontSize = '14px';
                button.style.fontWeight = 'bold';
                button.textContent = algorithm.title;
                button.id = algorithm.id;
                button.onclick = () => handleClick(algorithm.id);

                span.appendChild(button);
                itemDiv.appendChild(span);
                listItem.appendChild(itemDiv);

                // Append the list item to the sidebar
                sidebar.appendChild(listItem);
            });
        } catch (error) {
            return;
        }
    });
}

function loadAlgorithmsBySearchParam() {
    const url = new URL(window.location.href);

    const params = new URLSearchParams(url.search);

    var query = "";

    for (const [key, value] of params.entries()) {
        if (key == "query") {
            query = value;
        }
    }

    if (query.length > 0) {
        API_DisplayAlgorithmsBySearchQuery(query).then(ret => {
            try {
                const sidebar = document.querySelector('.list-group');
                sidebar.innerHTML = '';
                
                if (ret.length === 0) {
                    const noAlgorithmsMessage = document.createElement('p');
                    noAlgorithmsMessage.textContent = "Không tìm thấy thuật toán :(";
                    noAlgorithmsMessage.style.fontSize = '14px';
                    noAlgorithmsMessage.style.justifyContent = 'center';
                    noAlgorithmsMessage.style.alignItems = 'center';
                    noAlgorithmsMessage.style.textAlign = 'center';
                    noAlgorithmsMessage.style.alignContent = 'center';
                    noAlgorithmsMessage.style.marginTop = 'calc(1vh + 0.5vmin)';
                    sidebar.appendChild(noAlgorithmsMessage);
                    return;
                }
    
                // Assuming ret is an array of algorithms
                ret.forEach(algorithm => {
                    // Create a new list item for each algorithm
                    const listItem = document.createElement('li');
                    listItem.classList.add('list-group-item');
    
                    const itemDiv = document.createElement('div');
                    itemDiv.classList.add('item');
    
                    const span = document.createElement('span');
                    span.classList.add('label');
    
                    const button = document.createElement('a');
                    button.classList.add('btn');
                    button.style.width = '100%';
                    button.style.textAlign = 'center';
                    button.style.justifyContent = 'center';
                    button.style.padding = '20px';
                    button.style.fontSize = '14px';
                    button.style.fontWeight = 'bold';
                    button.textContent = algorithm.title;
                    button.id = algorithm.id;
                    button.onclick = () => handleClick(algorithm.id);
    
                    span.appendChild(button);
                    itemDiv.appendChild(span);
                    listItem.appendChild(itemDiv);
    
                    // Append the list item to the sidebar
                    sidebar.appendChild(listItem);
                });
            } catch (error) {
                return;
            }
        });
    } else {
        loadAlgorithms();
    }
}

function register() {
    const url = new URL(window.location.href);

    const params = new URLSearchParams(url.search);

    var username = "", fullname = "", email = "", password = "", repeatPassowrd = "", agreeTerm = "";

    for (const [key, value] of params.entries()) {
        if (key == "password") {
            password = value;
        } else if (key == "repeatPassword") {
            repeatPassowrd = value;
        } else if (key == "fullname") {
            fullname = value;
        } else if (key == "username") {
            username = value;
        } else if (key == "agreeTermOfService") {
            agreeTerm = value;
        }
    }
    
    if (agreeTerm != "true") {
        return;
    } else if (password != repeatPassowrd) {
        alert("Repeat password doesn not match with your password. Please try again");
        return;
    } else {
        API_register(username, password, fullname, username + "@gmail.com");
    }
}

function logout() {
    API_logout();
    window.location.href = "/";
}

function cancel() {
    $( function() {
        $( "#dialog-update-cancel" ).dialog({
            resizable: false,
            height: "auto",
            width: 512,
            modal: true,
            closeOnEscape: false,
            open: function(event, ui) {
                $(".ui-dialog-titlebar-close", ui.dialog || ui).hide();
            },
            buttons: {
                "Yes": function() {
                    hideSettings();
                    $(this).dialog("close");
                },
                Cancel: function() {
                    const saveButton = document.getElementById('saveButton');
                    const cancelButton = document.getElementById('cancelButton');
                    const btnBack = document.getElementById('btn-back');

                    saveButton.className = '';
                    cancelButton.className = '';
                    btnBack.className = 'btn btn-link btn-transparent';
                    saveButton.className = 'btn btn-primary';
                    cancelButton.className = 'btn btn-danger';
                    btnBack.className = 'btn btn-link btn-transparent';

                    window.location.href = "#";

                    $(this).dialog("close");
                }
            }
        });
    });
}

function updateUser() {
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    const passwordStrength = document.getElementById('password-strength');
    const fullname = document.getElementById('fullname');
    const email = document.getElementById('email');

    if (passwordStrength.innerHTML === "Your password strength: <b>Weak</b>") {
        $( function() {
            $( "#dialog-error" ).dialog({
                resizable: false,
                height: "auto",
                width: 512,
                modal: true,
                buttons: {
                    OK: function() {
                        $(this).dialog("close");
                    }
                }
            });
        });

        return;
    } else if (passwordStrength.innerHTML === "Your password strength: <b>Strong</b>") {
        $( function() {
            $( "#dialog-confirm" ).dialog({
                resizable: false,
                height: "auto",
                width: 512,
                modal: true,
                closeOnEscape: false,
                open: function(event, ui) {
                    $(".ui-dialog-titlebar-close", ui.dialog || ui).hide();
                },
                buttons: {
                    "Yes": function() {
                        $(this).dialog("close");
                        var isSucceeded = API_UpdateUser(id, username.value, password.value, fullname.value, email.value);
                        if (!isSucceeded) {
                            $( function() {
                                $( "#dialog-error-update" ).dialog({
                                    resizable: false,
                                    height: "auto",
                                    width: 512,
                                    modal: true,
                                    buttons: {
                                        OK: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            });
                        } else {
                            $( function() {
                                $( "#dialog-update-succeeded" ).dialog({
                                    resizable: false,
                                    height: "auto",
                                    width: 512,
                                    modal: true,
                                    buttons: {
                                        OK: function() {
                                            $(this).dialog("close");
                                            hideSettings();
                                        }
                                    }
                                });
                            });
                        }
                    },
                    Cancel: function() {
                        $(this).dialog("close");
                    }
                }
            });
        });
    } else {
        var isSucceeded = API_UpdateUser(id, username.value, password.value, fullname.value, email.value);
        if (!isSucceeded) {
            $( function() {
                $( "#dialog-error-update" ).dialog({
                    resizable: false,
                    height: "auto",
                    width: 512,
                    modal: true,
                    buttons: {
                        OK: function() {
                            $(this).dialog("close");
                        }
                    }
                });
            });
        } else {
            $( function() {
                $( "#dialog-update-succeeded" ).dialog({
                    resizable: false,
                    height: "auto",
                    width: 512,
                    modal: true,
                    buttons: {
                        OK: function() {
                            $(this).dialog("close");
                            hideSettings();
                        }
                    }
                });
            });
        }
    }
}

document.addEventListener("DOMContentLoaded", function() {
    // Attach the event listener to a static parent element
    document.body.addEventListener('click', function(event) {
        if (event.target.matches('.btn')) {
            // Remove the 'clicked' class from all buttons
            var buttons = document.querySelectorAll('.btn');
            buttons.forEach(function(btn) {
                btn.classList.remove('clicked');
            });

            event.target.classList.add('clicked');
        }
    });

    const searchInput = document.getElementById('searchInput');

    if (searchInput) {
        searchInput.addEventListener('input', function() {
            const searchValue = this.value;
    
            let url = new URL(window.location.href);
    
            url.searchParams.set('query', searchValue);
    
            history.pushState({}, '', url);
    
            loadAlgorithmsBySearchParam();
        });
    } else {
        return;
    }

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();

            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
});

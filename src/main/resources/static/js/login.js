(function() {
    'use strict';

    function init() {
        const usernameInput = document.getElementById('usernameInput');
        if (usernameInput) {
            usernameInput.focus();
        }
    }

    function handleLanguageChange() {
        const langCombo = document.getElementById('locales');
        if (langCombo && langCombo.value) {
            window.location.href = './login?lang=' + langCombo.value;
        }
    }

    function validateForm(event) {
        const usernameInput = document.getElementById('usernameInput');
        const errorDiv = document.getElementById('error');
        
        if (!usernameInput) {
            return true;
        }

        const username = usernameInput.value.trim();
        const isEmpty = !username || username.length === 0;

        if (isEmpty) {
            event.preventDefault();
            usernameInput.focus();
            
            if (errorDiv) {
                errorDiv.textContent = '';
            }
            
            return false;
        }

        if (errorDiv) {
            errorDiv.textContent = '';
        }

        return true;
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    document.addEventListener('DOMContentLoaded', function() {
        const loginForm = document.getElementById('loginForm');
        const langSelect = document.getElementById('locales');

        if (loginForm) {
            loginForm.addEventListener('submit', validateForm);
        }

        if (langSelect) {
            langSelect.addEventListener('change', handleLanguageChange);
        }
    });
})();


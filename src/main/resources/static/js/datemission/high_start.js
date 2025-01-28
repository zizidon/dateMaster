// high_start.js
document.addEventListener('DOMContentLoaded', function() {
    const hamburgerButton = document.querySelector('.hamburger-button');
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const backButton = document.querySelector('.back-button');
    const missionContainer = document.querySelector('.mission-container');
    let lastScrollTop = 0;

    // Create overlay element
    const overlay = document.createElement('div');
    overlay.className = 'overlay';
    document.body.appendChild(overlay);

    function toggleMenu() {
        hamburgerButton.classList.toggle('active');
        hamburgerMenu.classList.toggle('active');
        overlay.classList.toggle('active');

        // Disable scroll when menu is open
        if (hamburgerMenu.classList.contains('active')) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = '';
        }
    }

    // Hamburger menu toggle
    hamburgerButton.addEventListener('click', toggleMenu);
    overlay.addEventListener('click', toggleMenu);
    
    // Close menu when links are clicked
    const menuLinks = document.querySelectorAll('.hamburger-menu a');
    menuLinks.forEach(link => {
        link.addEventListener('click', () => {
            toggleMenu();
        });
    });

    // Form validation
    const missionForm = document.getElementById('missionForm');
    if (missionForm) {
        missionForm.addEventListener('submit', function(e) {
            const radioGroups = document.querySelectorAll('.radio-group');
            let allChecked = true;

            radioGroups.forEach(group => {
                const radios = group.querySelectorAll('input[type="radio"]');
                const checked = Array.from(radios).some(radio => radio.checked);
                if (!checked) {
                    allChecked = false;
                }
            });

            if (!allChecked) {
                e.preventDefault();
                alert('すべてのミッションの達成状況を選択してください。');
            }
        });
    }

    // Page load animations
    if (missionContainer && backButton) {
        [missionContainer, backButton].forEach(element => {
            element.style.opacity = '0';
            element.style.transform = 'translateY(20px)';

            setTimeout(() => {
                element.style.transition = 'all 0.5s ease';
                element.style.opacity = '1';
                element.style.transform = 'translateY(0)';
            }, 100);
        });
    }

    // Vibration effect for buttons
    const buttons = document.querySelectorAll('.submit-button, .back-button button');
    buttons.forEach(button => {
        button.addEventListener('touchstart', function() {
            if (window.navigator.vibrate) {
                window.navigator.vibrate(50);
            }
        });
    });
});
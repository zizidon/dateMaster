/* row_start.js */
document.addEventListener('DOMContentLoaded', function() {
    const hamburgerButton = document.querySelector('.hamburger-button');
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const backButton = document.querySelector('.back-button');
    const missionContainer = document.querySelector('.mission-container');
    const buttons = document.querySelectorAll('.submit-button, .back-button button');

    // オーバーレイ要素を作成
    const overlay = document.createElement('div');
    overlay.className = 'overlay';
    document.body.appendChild(overlay);

    function toggleMenu() {
        hamburgerButton.classList.toggle('active');
        hamburgerMenu.classList.toggle('active');
        overlay.classList.toggle('active');

        // メニューが開いているときはスクロールを無効化
        if (hamburgerMenu.classList.contains('active')) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = '';
        }
    }

    // ハンバーガーメニューの開閉
    hamburgerButton.addEventListener('click', toggleMenu);
    overlay.addEventListener('click', toggleMenu);
    
    // メニューリンクをクリックしたときにメニューを閉じる
    const menuLinks = document.querySelectorAll('.hamburger-menu a');
    menuLinks.forEach(link => {
        link.addEventListener('click', () => {
            toggleMenu();
        });
    });

    // ボタンのアニメーション効果
    buttons.forEach(button => {
        // タップまたはクリック時の振動効果
        button.addEventListener('touchstart', function() {
            if (window.navigator.vibrate) {
                window.navigator.vibrate(50);
            }
        });
    });

    // ページ読み込み時のアニメーション
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

    // フォームのバリデーション
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
                alert('すべてのミッションについて達成/未達成を選択してください。');
            }
        });
    }
});
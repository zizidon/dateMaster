/* rank_high.js */
document.addEventListener('DOMContentLoaded', function() {
    const hamburgerButton = document.querySelector('.hamburger-button');
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const backButton = document.querySelector('.back-button');
    const missionList = document.querySelector('.mission-list');
    const buttons = document.querySelectorAll('.update-btn, .start-btn, .back-button button');
    let lastScrollTop = 0;

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
    if (missionList && backButton) {
        [missionList, backButton].forEach(element => {
            element.style.opacity = '0';
            element.style.transform = 'translateY(20px)';

            setTimeout(() => {
                element.style.transition = 'all 0.5s ease';
                element.style.opacity = '1';
                element.style.transform = 'translateY(0)';
            }, 100);
        });
    }

    // 更新ボタンと開始ボタンのアニメーション
    const buttonContainer = document.querySelector('.button-container');
    if (buttonContainer) {
        buttonContainer.style.opacity = '0';
        buttonContainer.style.transform = 'translateY(20px)';

        setTimeout(() => {
            buttonContainer.style.transition = 'all 0.5s ease';
            buttonContainer.style.opacity = '1';
            buttonContainer.style.transform = 'translateY(0)';
        }, 300);
    }
});
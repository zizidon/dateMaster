document.addEventListener('DOMContentLoaded', function() {
    const hamburgerButton = document.querySelector('.hamburger-button');
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const evaluationContent = document.querySelector('.evaluation-content');
    const returnButton = document.querySelector('.return-button');

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

    // 戻るボタンのタッチ振動効果
    returnButton.addEventListener('touchstart', function() {
        if (window.navigator.vibrate) {
            window.navigator.vibrate(50);
        }
    });

    // ページ読み込み時のアニメーション
    if (evaluationContent) {
        evaluationContent.style.opacity = '0';
        evaluationContent.style.transform = 'translateY(20px)';

        setTimeout(() => {
            evaluationContent.style.transition = 'all 0.5s ease';
            evaluationContent.style.opacity = '1';
            evaluationContent.style.transform = 'translateY(0)';
        }, 100);
    }

    // 戻るボタンのアニメーション
    if (returnButton) {
        returnButton.style.opacity = '0';
        returnButton.style.transform = 'translateY(20px)';

        setTimeout(() => {
            returnButton.style.transition = 'all 0.5s ease';
            returnButton.style.opacity = '1';
            returnButton.style.transform = 'translateY(0)';
        }, 300);
    }
});
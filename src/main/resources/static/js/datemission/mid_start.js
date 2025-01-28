document.addEventListener('DOMContentLoaded', function() {
    const hamburgerButton = document.querySelector('.hamburger-button');
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const backButton = document.querySelector('.back-button');
    const missionContainer = document.querySelector('.mission-container');
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

    // フォーム送信前の確認
    const missionForm = document.getElementById('missionForm');
    if (missionForm) {
        missionForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // すべてのラジオボタンが選択されているか確認
            const missionItems = document.querySelectorAll('.mission-item');
            let allSelected = true;
            
            missionItems.forEach(item => {
                const radios = item.querySelectorAll('input[type="radio"]:checked');
                if (radios.length === 0) {
                    allSelected = false;
                }
            });

            if (!allSelected) {
                alert('すべてのミッションの達成状況を選択してください。');
                return;
            }

            // 確認ダイアログを表示
            if (confirm('デートを終了してよろしいですか？')) {
                this.submit();
            }
        });
    }

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

    // ラジオボタンのアニメーション効果
    const radioInputs = document.querySelectorAll('input[type="radio"]');
    radioInputs.forEach(radio => {
        radio.addEventListener('change', function() {
            // 選択された際の視覚的フィードバック
            const label = this.nextElementSibling;
            label.style.transform = 'scale(1.1)';
            setTimeout(() => {
                label.style.transform = 'scale(1)';
            }, 200);

            // タップ/クリック時の振動フィードバック
            if (window.navigator.vibrate) {
                window.navigator.vibrate(50);
            }
        });
    });
});
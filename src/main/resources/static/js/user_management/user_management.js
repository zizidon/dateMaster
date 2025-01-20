document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');

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

	hamburgerButton.addEventListener('click', toggleMenu);
	overlay.addEventListener('click', toggleMenu);

	// メニューリンクをクリックしたときにメニューを閉じる
	const menuLinks = document.querySelectorAll('.hamburger-menu a');
	menuLinks.forEach(link => {
		link.addEventListener('click', () => {
			toggleMenu();
		});
	});

	// アカウント削除ボタンの確認ダイアログ
	const deleteButton = document.querySelector('.button-group a:nth-child(3) button');
	if (deleteButton) {
		deleteButton.addEventListener('click', function(e) {
			const confirmed = confirm('本当にアカウントを削除しますか？この操作は取り消せません。');
			if (!confirmed) {
				e.preventDefault();
			}
		});
	}

	// ボタンのホバーエフェクト強化
	const buttons = document.querySelectorAll('button');
	buttons.forEach(button => {
		button.addEventListener('mouseenter', () => {
			button.style.transform = 'translateY(-5px)';
		});

		button.addEventListener('mouseleave', () => {
			button.style.transform = 'translateY(0)';
		});
	});
});
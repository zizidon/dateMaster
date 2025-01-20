document.addEventListener('DOMContentLoaded', function() {
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

	// 変更確定ボタンのクリック時の確認
	const confirmForm = document.querySelector('form');
	confirmForm.addEventListener('submit', function(event) {
		const confirmResult = confirm('名前を変更してもよろしいですか？');
		if (!confirmResult) {
			event.preventDefault();
		}
	});

	// 戻るボタンのクリック時の処理
	const backButtons = document.querySelectorAll('button[type="button"]');
	backButtons.forEach(button => {
		button.addEventListener('click', function() {
			history.back();
		});
	});
});
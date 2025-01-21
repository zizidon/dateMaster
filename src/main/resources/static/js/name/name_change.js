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

	// フォームバリデーション
	const form = document.querySelector('form');
	const nameInput = document.getElementById('new-name');

	form.addEventListener('submit', function(event) {
		const name = nameInput.value.trim();

		if (name === '') {
			event.preventDefault();
			alert('名前を入力してください。');
			nameInput.focus();
			return;
		}

		if (name.length > 50) {
			event.preventDefault();
			alert('名前は50文字以内で入力してください。');
			nameInput.focus();
			return;
		}
	});
});
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

	// 削除ボタンのインタラクション
	const deleteForm = document.querySelector('form[name="deleteForm"]');
	const deleteButton = document.querySelector('.delete-button');
	const cancelButton = document.querySelector('.cancel-button');

	function addButtonEffect(button) {
		button.classList.add('button-clicked');
		setTimeout(() => {
			button.classList.remove('button-clicked');
		}, 300);
	}

	

	cancelButton.addEventListener('click', function(e) {
		addButtonEffect(cancelButton);
	});

	// 背景のサブエフェクト
	function createBackgroundEffect() {
		const colors = ['#36454F', '#708090', '#2F4F4F'];
		let currentColorIndex = 0;

		setInterval(() => {
			document.body.style.background = `linear-gradient(135deg, ${colors[currentColorIndex]}, ${colors[(currentColorIndex + 1) % colors.length]})`;
			currentColorIndex = (currentColorIndex + 1) % colors.length;
		}, 5000);
	}

	createBackgroundEffect();

});
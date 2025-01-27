document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');
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

	// フォームのバリデーション
	const form = document.querySelector('form');
	form.addEventListener('submit', function(e) {
		const select = form.querySelector('select');
		if (!select.value) {
			e.preventDefault();
			alert('選択肢を選んでください。');
		}
	});

	// 選択肢の変更時のアニメーション
	const select = document.querySelector('select');
	select.addEventListener('change', function() {
		this.style.transform = 'scale(1.05)';
		setTimeout(() => {
			this.style.transform = 'scale(1)';
		}, 200);
	});

	// ボタンホバーエフェクト
	const buttons = document.querySelectorAll('button');
	buttons.forEach(button => {
		button.addEventListener('mouseenter', () => {
			button.style.transform = 'translateY(-2px)';
		});
		button.addEventListener('mouseleave', () => {
			button.style.transform = 'translateY(0)';
		});
	});
});
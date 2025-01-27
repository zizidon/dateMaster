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
	const inputs = form.querySelectorAll('input[required]');

	form.addEventListener('submit', function(e) {
		let isValid = true;
		inputs.forEach(input => {
			if (!input.value.trim()) {
				isValid = false;
				input.style.border = '2px solid red';
			} else {
				input.style.border = 'none';
			}
		});

		if (!isValid) {
			e.preventDefault();
			alert('すべての項目を入力してください。');
		}
	});

	// 入力フィールドのアニメーション効果
	inputs.forEach(input => {
		input.addEventListener('focus', function() {
			this.parentElement.classList.add('focused');
		});

		input.addEventListener('blur', function() {
			if (!this.value) {
				this.parentElement.classList.remove('focused');
			}
		});
	});
	
	
});
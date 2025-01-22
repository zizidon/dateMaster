document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	const passwordInput = document.getElementById('password');
	const formGroup = passwordInput.closest('.form-group');

	// パスワード入力のバリデーション
	function validatePassword() {
		if (!passwordInput.value) {
			showError('パスワードを入力してください');
			return false;
		}
		return true;
	}

	// エラーメッセージの表示
	function showError(message) {
		// 既存のエラーメッセージを削除
		const existingError = formGroup.querySelector('.password-error');
		if (existingError) {
			existingError.remove();
		}

		// 新しいエラーメッセージを作成
		const errorDiv = document.createElement('div');
		errorDiv.className = 'password-error';
		const errorMessage = document.createElement('p');
		errorMessage.textContent = message;
		errorDiv.appendChild(errorMessage);

		// フォームグループにエラークラスを追加
		formGroup.classList.add('has-error');

		// エラーメッセージを挿入
		formGroup.appendChild(errorDiv);
	}

	// エラーメッセージの非表示
	function hideError() {
		const errorDiv = formGroup.querySelector('.password-error');
		if (errorDiv) {
			errorDiv.remove();
			formGroup.classList.remove('has-error');
		}
	}

	// 入力フィールドの値が変更されたときにエラーを非表示
	passwordInput.addEventListener('input', hideError);

	// フォーム送信時のバリデーション
	form.addEventListener('submit', function(e) {
		if (!validatePassword()) {
			e.preventDefault();
		}
	});

	// ハンバーガーメニューの制御
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');
	const overlay = document.querySelector('.overlay');

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
		link.addEventListener('click', toggleMenu);
	});
});
document.addEventListener('DOMContentLoaded', function() {
	// トースト通知コンテナの作成
	const toastContainer = document.createElement('div');
	toastContainer.className = 'toast-container';
	document.body.appendChild(toastContainer);

	// アクティブなトーストを追跡
	let activeToast = null;

	// トースト通知を表示する関数
	function showToast(message, type = 'error') {
		// 既存のトーストがあれば削除
		if (activeToast) {
			activeToast.remove();
		}

		const toast = document.createElement('div');
		toast.className = `toast ${type}`;

		let icon = '⚠️';
		if (type === 'success') icon = '✅';
		else if (type === 'warning') icon = '⚠️';
		else if (type === 'error') icon = '❌';

		toast.innerHTML = `
            <span class="toast-icon">${icon}</span>
            <div class="toast-content">${message}</div>
            <button class="toast-close">×</button>
        `;

		toastContainer.appendChild(toast);
		activeToast = toast;

		// アニメーションのためのディレイ
		requestAnimationFrame(() => toast.classList.add('show'));

		// 閉じるボタンの処理
		const closeBtn = toast.querySelector('.toast-close');
		closeBtn.addEventListener('click', () => {
			toast.classList.remove('show');
			setTimeout(() => toast.remove(), 300);
			activeToast = null;
		});

		// 3秒後に自動的に消える
		setTimeout(() => {
			if (toast.isConnected) {
				toast.classList.remove('show');
				setTimeout(() => toast.remove(), 300);
				activeToast = null;
			}
		}, 3000);
	}

	// パスワード要件のチェック（特殊文字要件を削除）
	const requirements = [
		{ id: 'length', text: '8文字以上', regex: /.{8,}/ },
		{ id: 'uppercase', text: '大文字を含む', regex: /[A-Z]/ },
		{ id: 'lowercase', text: '小文字を含む', regex: /[a-z]/ },
		{ id: 'number', text: '数字を含む', regex: /[0-9]/ }
	];

	// パスワード要件リストの作成と配置
	const newPasswordInput = document.getElementById('new-password');
	const requirementsList = document.createElement('div');
	requirementsList.className = 'password-requirements';
	requirements.forEach(req => {
		const item = document.createElement('div');
		item.className = 'requirement-item';
		item.id = `req-${req.id}`;
		item.innerHTML = `
            <span class="requirement-icon">○</span>
            <span class="requirement-text">${req.text}</span>
        `;
		requirementsList.appendChild(item);
	});
	newPasswordInput.parentNode.appendChild(requirementsList);

	// パスワードの入力チェック
	newPasswordInput.addEventListener('input', function() {
		const password = this.value;
		requirements.forEach(req => {
			const item = document.getElementById(`req-${req.id}`);
			const isValid = req.regex.test(password);
			item.classList.toggle('valid', isValid);
			item.querySelector('.requirement-icon').textContent = isValid ? '✓' : '○';
		});
	});

	// フォームのバリデーション
	const form = document.querySelector('form');
	const currentPasswordInput = document.getElementById('current-password');
	const confirmPasswordInput = document.getElementById('confirm-password');

	form.addEventListener('submit', function(event) {
		event.preventDefault();

		const currentPassword = currentPasswordInput.value;
		const newPassword = newPasswordInput.value;
		const confirmPassword = confirmPasswordInput.value;

		// バリデーションチェック
		if (currentPassword.length < 8) {
			showToast('現在のパスワードを入力してください', 'error');
			currentPasswordInput.focus();
			return;
		}

		let isValid = requirements.every(req => req.regex.test(newPassword));
		if (!isValid) {
			showToast('パスワードの要件を満たしていません', 'warning');
			newPasswordInput.focus();
			return;
		}

		if (newPassword !== confirmPassword) {
			showToast('新しいパスワードと確認用パスワードが一致しません', 'error');
			confirmPasswordInput.focus();
			return;
		}

		if (currentPassword === newPassword) {
			showToast('新しいパスワードは現在のパスワードと異なるものを設定してください', 'warning');
			newPasswordInput.focus();
			return;
		}

		showToast('パスワードの変更を確認します', 'success');
		setTimeout(() => form.submit(), 1000);
	});

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
		document.body.style.overflow = hamburgerMenu.classList.contains('active') ? 'hidden' : '';
	}

	hamburgerButton.addEventListener('click', toggleMenu);
	overlay.addEventListener('click', toggleMenu);

	const menuLinks = document.querySelectorAll('.hamburger-menu a');
	menuLinks.forEach(link => {
		link.addEventListener('click', toggleMenu);
	});
});
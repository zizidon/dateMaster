document.addEventListener('DOMContentLoaded', function() {
	// 完了メッセージのアニメーション
	const completeMessage = document.querySelector('.complete-message');
	completeMessage.style.opacity = '0';
	completeMessage.style.transform = 'translateY(20px)';

	// ページ読み込み後にアニメーションを実行
	setTimeout(() => {
		completeMessage.style.transition = 'all 0.5s ease-out';
		completeMessage.style.opacity = '1';
		completeMessage.style.transform = 'translateY(0)';
	}, 100);
});
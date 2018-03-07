;(function ( window, $) {	
	$(document).ready(function () {
		gd();
	});
	var gd = function () {
		$('div.btn-left, div.btn-right').hover( function () {
		$( this ).find( 'span' ).toggleClass( 'hover' );
	}).click( function () {
		var $ul = $('#img-slider ul');
		var width = $('.slider-area').width();
		// console.log( $( this ).attr( 'class' ) == 'btn-left' );
		// if( ul.not( ":animated" ).length == 1 ){ // ���û������ִ�ж�����ul
		if ( $( this ).hasClass( 'btn-right' ) ) { //�ұߵİ�ť 
			$ul.stop().animate( { left: '-=' + width }, 500, function () {
				$ul.find( 'li:first' ).appendTo( $ul ).parent().css( 'left', 0 );
			} );
		} else { // ��ߵİ�ť
			$ul.find( 'li:last' ).prependTo( $ul ).parent().css( 'left', -width );
			$ul.stop().animate( { left: '+=' + width }, 500 );
		}
	});
	}
})(window,jQuery);
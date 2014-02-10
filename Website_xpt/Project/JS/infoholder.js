function tab(tab) {
    document.getElementById('tab1').style.display = 'none';
    document.getElementById('tab2').style.display = 'none';
    document.getElementById('li_tab1').setAttribute("class", "");
    document.getElementById('li_tab2').setAttribute("class", "");
    document.getElementById(tab).style.display = 'block';
    document.getElementById('li_' + tab).setAttribute("class", "active");
}
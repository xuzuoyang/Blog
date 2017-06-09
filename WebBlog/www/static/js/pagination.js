var pagination = Vue.extend({
    props: ['currentpage', 'totalpage', 'visiblepage'],
    data: function() {
        return {

        }
    },
    template: "<div class='page-bar'>"+
                "<ul class='uk-pagination'>"+
                    "<li v-if='currentpage != 1' class='uk-active'><span v-on:click='pageChange(currentpage-1)'><i class='uk-icon-angle-double-left'></i></span></li>"+
                    "<li v-if='currentpage == 1' class='uk-disabled'><span><i class='uk-icon-angle-double-left'></i></span></li>"+
                    "<li v-for='index in pagenums' v-bind:class='currentpage!=index ? \"uk-active\" : \"uk-disabled\"'><span v-on:click='currentpage!=index ? pageChange(index) : void(0)' v-text='index'></span></li>"+
                    "<li v-if='currentpage != totalpage' class='uk-active'><span v-on:click='pageChange(currentpage+1)'><i class='uk-icon-angle-double-right'></i></span></li>"+
                    "<li v-if='currentpage == totalpage' class='uk-disabled'><span><i class='uk-icon-angle-double-right'></i></span></li>"+
                    //"<li><a>共<i v-text='totalpage'></i>页</a></li>"+
                "</ul>"+
              "</div>",
    computed: {
        pagenums: function(){
            //初始化前后页边界
            var lowPage = 1;
            var highPage = this.totalpage;
            var pageArr = [];
            if(this.totalpage > this.visiblepage){//总页数超过可见页数时，进一步处理；
                var subVisiblePage = Math.ceil(this.visiblepage/2);
                if(this.currentpage > subVisiblePage && this.currentpage < this.totalpage - subVisiblePage +1){//处理正常的分页
                    lowPage = this.currentpage - subVisiblePage;
                    highPage = this.currentpage + subVisiblePage -1;
                }else if(this.currentpage <= subVisiblePage){//处理前几页的逻辑
                    lowPage = 1;
                    highPage = this.visiblepage;
                }else{//处理后几页的逻辑
                    lowPage = this.totalpage - this.visiblepage + 1;
                    highPage = this.totalpage;
                }
            }
            //确定了上下page边界后，要准备压入数组中了
            while(lowPage <= highPage){
                pageArr.push(lowPage);
                lowPage++;
            }
            return pageArr;
        }
    },
    methods: {
        pageChange: function(selected_page){
            if (this.currentpage != selected_page) {
                this.currentpage = selected_page;
                this.$emit('page-change', selected_page); //父子组件间的通信：==>子组件通过$diapatch(),分发事件，父组件冒泡通过v-on:page-change监听到相应的事件；
            };
        }
    }
});

window.pagination = pagination;

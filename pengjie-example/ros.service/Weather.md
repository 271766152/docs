## 2.1 Weather

---

### 2.1.1 ����˵��

> (**Weather**)

### 2.1.2 ��ͼ˵��

> �÷�����Ҫ��8����ͼ(**Action**)����Ӧ�Ĺ��ܡ����ƽ������¡�
> 
> 1.**PollutionADay**
> 
>   | ���� | ���� | ���� | ���� | ���� | �������� | �����ֶ� | �������� |
>   | --- | --- | --- | --- | --- | --- | --- | --- |
>   | PollutionADay |   |   | weather | params | response_data | response_field |   |
>
>
> * **����(params)����**
>
>   | �������� | �������� | �������� |
>   | --- | --- | --- |
>   | peri | @sys.entity.time_period |   |
>   | city | @sys.entity.city |   |
>   | date | @sys.date |   |
>   | time | @sys.date_time |   |
>
> * **��������(response_data)����**
> ```
>
> ```
>
> * **�����ֶ�(response_field)����**
>
>   | �ֶ����� | �ֶ����� |
>   | --- | --- |
>   |  |  |
> 2.**WeatherForADay**
> 
>   | ���� | ���� | ���� | ���� | ���� | �������� | �����ֶ� | �������� |
>   | --- | --- | --- | --- | --- | --- | --- | --- |
>   | WeatherForADay |   |   | weather | params | response_data | response_field |   |
>
>
> * **����(params)����**
>
>   | �������� | �������� | �������� |
>   | --- | --- | --- |
>   | peri | @sys.entity.time_period |   |
>   | time | @sys.date_time |   |
>   | city | @sys.entity.city |   |
>   | date | @sys.date |   |
>
> * **��������(response_data)����**
> ```
>
> ```
>
> * **�����ֶ�(response_field)����**
>
>   | �ֶ����� | �ֶ����� |
>   | --- | --- |
>   |  |  |
> 3.**WeatherForDays**
> 
>   | ���� | ���� | ���� | ���� | ���� | �������� | �����ֶ� | �������� |
>   | --- | --- | --- | --- | --- | --- | --- | --- |
>   | WeatherForDays |   |   | weather | params | response_data | response_field |   |
>
>
> * **����(params)����**
>
>   | �������� | �������� | �������� |
>   | --- | --- | --- |
>   | duration | @sys.date_duration |   |
>   | city | @sys.entity.city |   |
>
> * **��������(response_data)����**
> ```
>
> ```
>
> * **�����ֶ�(response_field)����**
>
>   | �ֶ����� | �ֶ����� |
>   | --- | --- |
>   |  |  |
> 4.**PollutionForDays**
> 
>   | ���� | ���� | ���� | ���� | ���� | �������� | �����ֶ� | �������� |
>   | --- | --- | --- | --- | --- | --- | --- | --- |
>   | PollutionForDays |   |   | weather | params | response_data | response_field |   |
>
>
> * **����(params)����**
>
>   | �������� | �������� | �������� |
>   | --- | --- | --- |
>   | duration | @sys.date_duration |   |
>   | city | @sys.entity.city |   |
>
> * **��������(response_data)����**
> ```
>
> ```
>
> * **�����ֶ�(response_field)����**
>
>   | �ֶ����� | �ֶ����� |
>   | --- | --- |
>   |  |  |
